import re,urllib
import codecs
from BeautifulSoup import BeautifulSoup

#x = urllib.urlopen("http://www.cnn.com/2011/US/01/06/maryland.security.incident/index.html?hpt=T1")
#data = x.read()

#filepath passed in by java
filepath

#fXml = open('/data/www_cnn_com/1-raw.html', 'r')
fXml = open(filepath, 'r')
data = fXml.read()

exp = r"<[^<]+?(?://|--)>"  # replace badly formatted comments
#data = re.sub(exp,"",data)

soup = BeautifulSoup(data)

#test = soup.findAll('body') #'a' for anchor tags, 'body' for body tags, etc

#body = soup.body.prettify()

#print 'len(body) = %s' % len(body)

h1 = soup.h1

next = h1.findNextSiblings('div')

print 'len(next) = %s' % len(next)

title = '%s' % (h1.string)

print title

strHtml = ''
for element in next:
    strHtml += '%s' % element.prettify()
