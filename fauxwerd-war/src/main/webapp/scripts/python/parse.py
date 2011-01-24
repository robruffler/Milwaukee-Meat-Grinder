import re,urllib
import codecs
import logging
from BeautifulSoup import BeautifulSoup

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

logger.debug("opening file %s" % filepath)

fXml = open(filepath, 'r')

logger.debug("reading file %s" % filepath)

data = fXml.read()

logger.debug("souping data")

soup = BeautifulSoup(data)

logger.debug("data souped - fetching h1")

h1 = soup.h1

logger.debug("h1 fetched - fetching titleTag")

titleTag = soup.title

logger.debug("titleTag fetched - determining content title")

if h1 != None:
    logger.debug("we have an h1!")
    
    #check if the contents of h1 are a string, if not, parse what's inside    
    if h1.string != None:
        title = '%s' % (h1.string)
    else:
        title = '%s' % (h1.next.renderContents().decode('utf-8'));
    
elif titleTag != None: 
    logger.debug("no h1 here - but we have a title tag")
    
    title = '%s' % (titleTag.string)
        
else:
    logger.debug("no titleTag here - wah wah wah");

logger.debug("title = %s" % title)

logger.debug("fetching body")

body = soup.body

logger.debug("fetched body, rendering contents")

next = body.renderContents().decode('utf-8')

logger.debug("contents rendered, converting to string")
        
strHtml = ''
for element in next:
    strHtml += '%s' % element
 
logger.debug("parse.py all done!")