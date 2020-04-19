import requests
from bs4 import BeautifulSoup
import csv
import urllib.parse


# 构造函数获取歌手信息
def get_artists(url):
    headers = {
        'Accept':
        'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8',
        'Accept-Encoding':
        'gzip, deflate',
        'Accept-Language':
        'zh-CN,zh;q=0.9',
        'Connection':
        'keep-alive',
        'Cookie':
        '_iuqxldmzr_=32; _ntes_nnid=0e6e1606eb78758c48c3fc823c6c57dd,1527314455632; '
        '_ntes_nuid=0e6e1606eb78758c48c3fc823c6c57dd; __utmc=94650624; __utmz=94650624.1527314456.1.1.'
        'utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); WM_TID=blBrSVohtue8%2B6VgDkxOkJ2G0VyAgyOY;'
        ' JSESSIONID-WYYY=Du06y%5Csx0ddxxx8n6G6Dwk97Dhy2vuMzYDhQY8D%2BmW3vlbshKsMRxS%2BJYEnvCCh%5CKY'
        'x2hJ5xhmAy8W%5CT%2BKqwjWnTDaOzhlQj19AuJwMttOIh5T%5C05uByqO%2FWM%2F1ZS9sqjslE2AC8YD7h7Tt0Shufi'
        '2d077U9tlBepCx048eEImRkXDkr%3A1527321477141; __utma=94650624.1687343966.1527314456.1527314456'
        '.1527319890.2; __utmb=94650624.3.10.1527319890',
        'Host':
        'music.163.com',
        'Referer':
        'http://music.163.com/',
        'Upgrade-Insecure-Requests':
        '1',
        'User-Agent':
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) '
        'Chrome/66.0.3359.181 Safari/537.36'
    }
    r = requests.get(url, headers=headers)
    soup = BeautifulSoup(r.text, 'html5lib')
    for artist in soup.find_all('a',
                                attrs={'class': 'nm nm-icn f-thide s-fc0'}):
        artist_name = artist.string
        artist_id = artist['href'].replace('/artist?id=', '').strip()
        try:
            data.append((artist_id, artist_name))
        except Exception as msg:
            print(msg)


ls1 = [1001, 1002, 1003]  # id的值
ls2 = [-1]  # initial的值
csvfile = open('E:/pyProject/crawl/netcloud/music_163_artists.csv',
               'w',
               encoding='utf-8',newline="")  # 文件存储的位置
writer = csv.writer(csvfile)
writer.writerow(['artist_id', 'artist_name'])
data = []
for i in ls1:
    for j in ls2:
        url = 'http://music.163.com/discover/artist/cat?id=' + str(
            i) + '&initial=' + str(j)
        print(url)
        get_artists(url)
writer.writerows(data)
csvfile.close()
