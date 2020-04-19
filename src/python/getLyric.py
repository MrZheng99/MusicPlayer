# coding='utf-8'
import requests
import json
import re
import csv
import time


def getLyric(songId, songName):
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
    url = 'http://music.163.com/api/song/lyric?id={}&lv=1&kv=1&tv=-1'.format(
        songId)

    r = requests.post(url, headers=headers)
    # 状态码
    print(r.status_code)
    text = json.loads(r.content)
    lyric = str(text["lrc"]["lyric"])
    lyric = re.sub('(\[).*?(\])', "", lyric, flags=re.S)

    with open(r'E:/pyProject/crawl/netcloud/lyric/{0}.txt'.format(
            songName),
              "w",
              encoding="utf-8") as f:
        f.write(lyric)


if __name__ == "__main__":

    filename = "E:/pyProject/crawl/netcloud/music163_songs/songs.csv"
    listId = []
    listName = []
    with open(filename, 'r', encoding="utf-8", newline="") as f:
        reader = csv.DictReader(f)
        for row in reader:
            listId.append(row["id"])
            listName.append(row["name"])
            
    for i in range(400):
        getLyric(listId[i], listName[i])
        time.sleep(1)
