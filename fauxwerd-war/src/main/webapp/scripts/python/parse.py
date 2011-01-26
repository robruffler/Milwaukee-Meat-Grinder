import re,urllib
import codecs
import logging
from BeautifulSoup import BeautifulSoup, SoupStrainer

# create logger
logger = logging.getLogger("parse.py")
logger.setLevel(logging.DEBUG)

# create console handler and set level to debug
ch = logging.StreamHandler()
ch.setLevel(logging.DEBUG)

# create formatter
formatter = logging.Formatter("[%(asctime)s,%(msecs)03d] %(levelname)s: %(name)s - %(message)s", "%d %b %Y %H:%M:%S")

# add formatter to ch
ch.setFormatter(formatter)

# add ch to logger
logger.addHandler(ch)

logger.debug("in parse.py")

#filepath passed in by java
filepath

#siteHostname passed in by java
siteHostname

logger.debug("opening file %s" % filepath)

fXml = open(filepath, 'r')

logger.debug("reading file %s" % filepath)

data = fXml.read()

logger.debug("straining soup for body")

bodyTag = SoupStrainer('body')

logger.debug("soup strained, souping data")

soup = BeautifulSoup(data, parseOnlyThese=bodyTag)

logger.debug("data souped - fetching body")

body = soup.body

logger.debug("fetched body, removing bad tags")

bannedTags = ['script', 'style', 'embed', 'object', 'input', 'textarea', 'select', 'noscript', 'iframe']

for tag in bannedTags:
    tags = soup.findAll(tag)    
    [banned.extract() for banned in tags]
    logger.debug("removed %s %s tags" % (len(tags), tag));
    
logger.debug("fixing img tags");

images = soup.findAll('img', src=re.compile("^/"))

logger.debug("fixing %s img tags" % len(images));

for img in images:
    img['src'] = "http://%s%s" % (siteHostname, img['src'])

logger.debug("fixing href tags");

hrefs = soup.findAll('a', href=re.compile("^/"))

logger.debug("fixing %s href tags" % len(hrefs));

for href in hrefs:
    href['href'] = "http://%s%s" % (siteHostname, href['href'])

    
logger.debug("filtering some divs");

divs = soup.findAll('div', id=re.compile("^nav|^header|^footer|^disqus|^dsq|^fb|^link|^menu|twitter-iframe|^upnext|^right_sidebar|^article_tools|^enlarge"))

logger.debug("removing %s div tags" % len(divs));

for div in divs:
    div.extract()        

logger.debug("filtering some more divs by class");

bannedDivClasses = ['entry-info', 'widget-area']

for bannedDivClass in bannedDivClasses:
    
    divsByClass = soup.findAll('div', {'class': bannedDivClass})

    logger.debug("removing %s more div tags" % len(divsByClass))

    for divByClass in divsByClass:
        divByClass.extract()        

logger.debug("finishing up")
        
strHtml = body.renderContents()
 
logger.debug("all done!")