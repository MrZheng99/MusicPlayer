# -*- coding: utf-8 -*-

from selenium import webdriver
from bs4 import BeautifulSoup
import time
import csv


def parse_html_page(html):
    # 使用双引号会出现 Unresolve reference
    # pattern = '<span class="txt"><a href="/song?id=(\d*)"><b title="(.*?)">'
    # 这里是使用lxml解析器进行解析,lxml速度快,文档容错能力强,也能使用html5lib
    soup = BeautifulSoup(html, 'lxml')
    idTitles = soup.find_all('span', 'txt')
    times = soup.find_all('span', 'u-dur')
    images = soup.find_all('span', 'icn icn-share')
    albums = soup.find_all('td', 'w4')

    return {
        "idTitles": idTitles,
        "times": times,
        "images": images,
        "albums": albums
    }


'''
# 这里以获取薛之谦的热门歌曲为例
url = "https://music.163.com/#/artist?id=5781"
html = get_html_src(url)
'''


def transToTime(stringTime):
    listTime = stringTime.split(":")
    listLength = len(listTime)
    if listLength == 2:
        return int(str(listTime[1]).strip()) * 1000 + int(str(listTime[0]).strip()) * 60000
    if listLength == 1:
        return int(str(listTime[1]).strip()) * 1000


# 将获得的歌手的热门歌曲id和名字写入csv文件
def write_to_csv(items, artist_name):
    artist_name = str(artist_name).replace("\"","")
    with open("E:/pyProject/crawl/netcloud/music163_songs/songs.csv",
              "a",
              encoding='utf-8',
              newline="") as csvfile:
        writer = csv.writer(csvfile)
        idTitles = items["idTitles"]
        times = items["times"]
        images = items["images"]
        albums = items["albums"]

        for i in range(10):
            data = []
            songId = idTitles[i].a['href'].replace('/song?id=', '')
            songName = idTitles[i].b['title']
            data.append(songId)
            data.append(songName)
            data.append(transToTime(times[i].text))
            data.append(albums[i].div.a["title"])
            data.append(artist_name)
            data.append(
                "http://music.163.com/song/media/outer/url?id={}.mp3".format(
                    songId))
            data.append(images[i]["data-res-pic"])
            data.append("src/lyric/{}.txt".format(songName))
            writer.writerow(data)
        '''
            # 可视化显示
            print('歌曲id:', item.a['href'].replace('/song?id=', ''))
            song_name = item.b['title']
            print('歌曲名字:', song_name)
        '''
    csvfile.close()


# 获取歌手id和歌手姓名
def read_csv():

    with open("E:/pyProject/crawl/netcloud/id.csv",
              "r",
              encoding="utf-8",
              newline="") as csvfile:

        reader = csv.reader(csvfile, delimiter=',')
        next(csvfile)
        for row in csvfile:
            artist_id, artist_name = str(row).split(",")
            if str(artist_id) is "artist_id":
                continue
            else:
                yield artist_id, artist_name
    # 当程序的控制流程离开with语句块后, 文件将自动关闭


def main():
    # 可以任意选择浏览器,前提是要配置好相关环境,更多请参考selenium官方文档

    driver_path = r"E:/pyProject/crawl/my_selenium/chromedriver.exe"
    driver = webdriver.Chrome(executable_path=driver_path)

    # 避免多次打开浏览器
    for readcsv in read_csv():
        artist_id, artist_name = readcsv
        url = "https://music.163.com/#/artist?id=" + str(artist_id)
        print("正在获取{}的热门歌曲...".format(artist_name))
        driver.get(url)
        # 切换成frame
        driver.switch_to_frame("g_iframe")
        # 休眠3秒,等待加载完成!
        time.sleep(3)
        html = driver.page_source
        items = parse_html_page(html)
        print("{}的热门歌曲获取完成!".format(artist_name))
        print("开始将{}的热门歌曲写入文件".format(artist_name))
        write_to_csv(items, artist_name)
        print("{}的热门歌曲写入到本地成功!".format(artist_name))


if __name__ == "__main__":
    with open("E:/pyProject/crawl/netcloud/music163_songs/songs.csv",
              "a",
              encoding='utf-8',
              newline="") as csvfile:
        writer = csv.writer(csvfile)
        writer.writerow([
            "id", "name", "duration", "albumName", "singerName", "songUrl",
            "imageUrl", "lyricUrl"
        ])
    main()
