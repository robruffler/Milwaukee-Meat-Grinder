package com.fauxwerd.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fauxwerd.util.parse.Link;
import com.fauxwerd.util.parse.ScoredElement;

/*
 * Number of html parsing utility methods based on readability js
 */

public class ParseUtil {
	
	private static Logger log = LoggerFactory.getLogger(ParseUtil.class);
	
//    flags:                   0x1 | 0x2 | 0x4,   /* Start with all flags set. */
//
//    /* constants */
//    FLAG_STRIP_UNLIKELYS:     0x1,
//    FLAG_WEIGHT_CLASSES:      0x2,
//    FLAG_CLEAN_CONDITIONALLY: 0x4,
		
	private static int FLAG_STRIP_UNLIKELYS = 1;
	private static int FLAG_WEIGHT_CLASSES = 2;
	private static int FLAG_CLEAN_CONDITIONALLY = 4;
	
	private static int[] FLAGS = {FLAG_STRIP_UNLIKELYS, FLAG_WEIGHT_CLASSES, FLAG_CLEAN_CONDITIONALLY };	

	
	/**
     * All of the regular expressions in use within readability.
     * Defined up here so we don't instantiate them repeatedly in loops.
     **/

	//TODO compile as regex patterns?
	@SuppressWarnings("serial")
	public static final Map<String, Pattern> REGEX =
		new HashMap<String, Pattern>()
		{
			{
				put("unlikelyCandidates", Pattern.compile("combx|comment|community|disqus|extra|foot|header|menu|remark|rss|shoutbox|sidebar|sponsor|ad-break|agegate|pagination|pager|popup|tweet|twitter", Pattern.CASE_INSENSITIVE)); //case-insensitive
				put("okMaybeItsACandidate", Pattern.compile("and|article|body|column|main|shadow", Pattern.CASE_INSENSITIVE)); //case-insensitive
				put("positive", Pattern.compile("article|body|content|entry|hentry|main|page|pagination|post|text|blog|story", Pattern.CASE_INSENSITIVE)); //case-insensitive
				put("negative", Pattern.compile("combx|comment|com-|contact|foot|footer|footnote|masthead|media|meta|outbrain|promo|related|scroll|shoutbox|sidebar|sponsor|shopping|tags|tool|widget", Pattern.CASE_INSENSITIVE)); //case-insensitive
				put("extraneous", Pattern.compile("print|archive|comment|discuss|e[\\-]?mail|share|reply|all|login|sign|single", Pattern.CASE_INSENSITIVE)); //case-insensitive
				put("divToPElements", Pattern.compile("<(a|blockquote|dl|div|img|ol|p|pre|table|ul)", Pattern.CASE_INSENSITIVE)); //case-insensitive
				put("replaceBrs", Pattern.compile("(<br[^>]*>[ \n\r\t]*){2,}", Pattern.CASE_INSENSITIVE)); //global replace & case insensitive
				put("replaceFonts", Pattern.compile("<(\\/?)font[^>]*>", Pattern.CASE_INSENSITIVE)); //global replace & case insensitive
				put("trim", Pattern.compile("^\\s+|\\s+$")); //global replace
				put("normalize", Pattern.compile("\\s{2,}")); //global replace
				put("killBreaks", Pattern.compile("(<br\\s*\\/?>(\\s|&nbsp;?)*){1,}")); //global replace
				put("videos", Pattern.compile("http:\\/\\/(www\\.)?(youtube|vimeo)\\.com", Pattern.CASE_INSENSITIVE)); //case-insensitive
				put("skipFootnoteLink", Pattern.compile("^\\s*(\\[?[a-z0-9]{1,2}\\]?|^|edit|citation needed)\\s*$", Pattern.CASE_INSENSITIVE)); //case-insensitive
				put("nextLink", Pattern.compile("(next|weiter|continue|>([^\\|]|$)|»([^\\|]|$))", Pattern.CASE_INSENSITIVE)); //case-insensitive
				put("prevLink", Pattern.compile("(prev|earl|old|new|<|«)", Pattern.CASE_INSENSITIVE)); //case-insensitive
			}
		};	
	
    /**
     * Initialize a node with the readability object. Also checks the
     * className/id for special names to add to its score.
     *
     * @param Element
     * @return void
    **/
    public static ScoredElement initializeNode(Element node) {
    	ScoredElement scoredElement = new ScoredElement(node, 0);
    	
//    	node.readability = {"contentScore": 0};         

//        switch(scoredElement.tagName().toUpperCase()) {
//            case "DIV":
//                scoredElement.incrementScore(5);
//                break;
//
//            case "PRE":
//            case "TD":
//            case "BLOCKQUOTE":
//                scoredElement.incrementScore(3);
//                break;
//                
//            case "ADDRESS":
//            case "OL":
//            case "UL":
//            case "DL":
//            case "DD":
//            case "DT":
//            case "LI":
//            case "FORM":
//                scoredElement.incrementScore(-3);
//                break;
//
//            case "H1":
//            case "H2":
//            case "H3":
//            case "H4":
//            case "H5":
//            case "H6":
//            case "TH":
//                scoredElement.incrementScore(-5);
//                break;
//        }
    	
    	if (scoredElement.tagName().equalsIgnoreCase("DIV")) scoredElement.incrementScore(5);
    	if (scoredElement.tagName().equalsIgnoreCase("BLOCKQUOTE")) scoredElement.incrementScore(3);
    	if (scoredElement.tagName().equalsIgnoreCase("FORM")) scoredElement.incrementScore(-3);
    	if (scoredElement.tagName().equalsIgnoreCase("TH")) scoredElement.incrementScore(-5);
       
        scoredElement.incrementScore(getClassWeight(node));
        
        return scoredElement;
    }	
    
    /**
     * Get an elements class/id weight. Uses regular expressions to tell if this 
     * element looks good or bad.
     *
     * @param Element
     * @return number (Integer)
    **/
    public static int getClassWeight(Element e) {
//TODO revisit
//        if(!readability.flagIsActive(readability.FLAG_WEIGHT_CLASSES)) {
//            return 0;
//        }

        int weight = 0;

        /* Look for a special classname */
        if (e.className() != null && !"".equals(e.className())) {
        	if (REGEX.get("negative").matcher(e.className()).find()) {
        		weight -= 25;
        	}
        	if (REGEX.get("positive").matcher(e.className()).find()) {
        		weight += 25;
        	}
        }
//        if (typeof(e.className) === 'string' && e.className !== '') {
//            if(e.className.search(readability.regexps.negative) !== -1) {
//                weight -= 25; }
//
//            if(e.className.search(readability.regexps.positive) !== -1) {
//                weight += 25; }
//        }

        /* Look for a special ID */
        if (e.id() != null && !"".equals(e.id())) {
        	if (REGEX.get("negative").matcher(e.id()).find()) {
        		weight -= 25;
        	}
        	if (REGEX.get("positive").matcher(e.id()).find()) {
        		weight += 25;
        	}
        }
        
//        if (typeof(e.id) === 'string' && e.id !== ''){
//            if(e.id.search(readability.regexps.negative) !== -1) {
//                weight -= 25; }
//
//            if(e.id.search(readability.regexps.positive) !== -1) {
//                weight += 25; }
//        }

        return weight;
    }    
	
    /**
     * Prepare the HTML document for readability to scrape it.
     * This includes things like stripping javascript, CSS, and handling terrible markup.
     * 
     * @return void
     **/
    public static void prepDocument(Document document) {
        /**
         * In some cases a body element can't be found (if the HTML is totally hosed for example)
         * so we create a new body node and append it to the document.
         */
    	    	
        if(document.body() == null) {
            Element body = document.createElement("body");
            Element head = document.head();
            head.after(body.html());                        
        }

        document.body().attr("id", "fauxwerdBody");

//TODO need to understand how to grab this frame info        
        List<Element> frames = document.getElementsByTag("frame");
        if(frames.size() > 0) {
            Element bestFrame = null;
            int bestFrameSize = 0;    /* The frame to try to run readability upon. Must be on same domain. */
            int biggestFrameSize = 0; /* Used for the error message. Can be on any domain. */

            for(int frameIndex = 0; frameIndex < frames.size(); frameIndex++) {
//TODO revisit offsetWidth & offsetHeight            	
//                int frameSize = frames.get(frameIndex).offsetWidth + frames[frameIndex].offsetHeight;
                boolean canAccessFrame = false;
//                try {
//TODO revisit contentWindow                	
//                    Element frameBody = frames.get(frameIndex).contentWindow.document.body;
//                    canAccessFrame = true;
//                }
//                catch(eFrames) {
//                    dbg(eFrames);
//                }

//                if(frameSize > biggestFrameSize) {
//                    biggestFrameSize         = frameSize;
//                    readability.biggestFrame = frames[frameIndex];
//                }
                
//                if(canAccessFrame && frameSize > bestFrameSize) {
//                    readability.frameHack = true;
//    
//                    bestFrame = frames[frameIndex];
//                    bestFrameSize = frameSize;
//                }
            }

//            if(bestFrame) {
//            	Element body = new Element(Tag.valueOf("body"), "");
//                newBody.innerHTML = bestFrame.contentWindow.document.body.innerHTML;
//                newBody.style.overflow = 'scroll';
//                document.body = newBody;
                
//                var frameset = document.getElementsByTagName('frameset')[0];
//                if(frameset) {
//                    frameset.parentNode.removeChild(frameset); }
//            }
        }

        /* Remove all stylesheets */
        for (Element styleSheet : document.getElementsByTag("style")) {
        	if (styleSheet.attr("href") != null  && styleSheet.attr("href").lastIndexOf("readability") == -1) {
        		styleSheet.remove();
        	}
        	styleSheet.text("");
        }
//        for (var k=0;k < document.styleSheets.length; k+=1) {
//            if (document.styleSheets[k].href !== null && document.styleSheets[k].href.lastIndexOf("readability") === -1) {
//                document.styleSheets[k].disabled = true;
//            }
//        }

        /* Remove all style tags in head (not doing this on IE) - TODO: Why not? */
//        var styleTags = document.getElementsByTagName("style");
//        for (var st=0;st < styleTags.length; st+=1) {
//            styleTags[st].textContent = "";
//        }

        /* Turn all double br's into p's */
        /* Note, this is pretty costly as far as processing goes. Maybe optimize later. */
//        document.body.innerHTML = document.body.innerHTML.replace(readability.regexps.replaceBrs, '</p><p>').replace(readability.regexps.replaceFonts, '<$1span>');

                
        String fixedHtml = REGEX.get("replaceBrs").matcher(document.body().html()).replaceAll("</p><p>");
        fixedHtml = REGEX.get("replaceFonts").matcher(fixedHtml).replaceAll("<$1span>");
        document.body().html(fixedHtml);
                
    }
    
    /**
     * Get the article title as an H1.
     *
     * @return void
     **/
    public static Element getArticleTitle(Document document) {
        String curTitle = "";
        String origTitle = "";

        curTitle = origTitle = document.title();
        
        //TODO can probably remove this, don't think it can ever occur
        if(!(curTitle instanceof String)) { /* If they had an element with id "title" in their HTML */
        	curTitle = origTitle = document.getElementsByTag("title").get(0).text();             
        }
        
        if(curTitle.matches(" [\\|\\-] ")) {
            curTitle = origTitle.replaceAll("(.*)[\\|\\-] .*","$1"); //global replace case insensitive
            
            if(curTitle.split(" ").length < 3) {
                curTitle = origTitle.replaceAll("[^\\|\\-]*[\\|\\-](.*)","$1"); //global replace case insensitive
            }
        }
        else if(curTitle.indexOf(": ") != -1) {
            curTitle = origTitle.replaceAll(".*:(.*)", "$1"); //global replace case insensitive

            if(curTitle.split(" ").length < 3) {
                curTitle = origTitle.replaceAll("[^:]*[:](.*)","$1"); //global replace case insensitive
            }
        }
        else if(curTitle.length() > 150 || curTitle.length() < 15) {
            Elements hOnes = document.getElementsByTag("h1");
            if(hOnes.size() == 1) {
                curTitle = hOnes.get(0).text();
            }
        }
        
        curTitle = REGEX.get("trim").matcher(curTitle).replaceAll("");

        if(curTitle.split(" ").length <= 4) {
            curTitle = origTitle;
        }
        
        Element articleTitle = new Element(Tag.valueOf("H1"),"");
        articleTitle.html(curTitle);
        
        return articleTitle;
    }    

    /***
     * grabArticle - Using a variety of metrics (content score, classname, element types), find the content that is
     *               most likely to be the stuff a user wants to read. Then return it wrapped up in a div.
     *
     * @param page a document to run upon. Needs to be a full document, complete with body.
     * @return Element
    **/
//    public static Element grabArticle(Document page) {
    public static Element grabArticle(Element page) {

        boolean stripUnlikelyCandidates = flagIsActive(FLAG_STRIP_UNLIKELYS);
//TOOD revisit how this can be implemented
//        boolean isPaging = (page != null) ? true: false;
        boolean isPaging = false;

//        if (log.isDebugEnabled()) log.debug(String.format("in grabArticle, stripUnlikelyCandidates = %s", stripUnlikelyCandidates));
        
//in original js version, initial call to grabArticle does not include parameter, but we will always pass one so this line is unnecessary
//        page = page != null ? page : document.body;
         
        String pageCacheHtml = page.html();

//        Elements allElements = page.getElementsByTag("*");
//        Elements allElements = page.getAllElements();
        Elements allElements = page.select("*");
        
//        if (log.isDebugEnabled()) log.debug(String.format("article has %s elements", allElements.size()));

        /**
         * First, node prepping. Trash nodes that look cruddy (like ones with the class name "comment", etc), and turn divs
         * into P tags where they have been used inappropriately (as in, where they contain no other block level elements.)
         *
         * Note: Assignment from index for performance. See http://www.peachpit.com/articles/article.aspx?p=31567&seqNum=5
         * TODO: Shouldn't this be a reverse traversal?
        **/
//        Element node = null;
        Elements nodesToScore = new Elements();
        
//TODO verify this works still        
//        for(int nodeIndex = 0; nodeIndex < allElements.size(); nodeIndex++) {
        for(Element node : allElements) {
//        	node = allElements.get(nodeIndex);
        	        	
//        	if (log.isDebugEnabled()) log.debug(String.format("node = %s", node));        	
//        	if (log.isDebugEnabled()) log.debug(String.format("node.parent() = %s", node.parent()));
        	
            /* Remove unlikely candidates */
            if (stripUnlikelyCandidates) {
                String unlikelyMatchString = node.className() + node.id();
//                if (log.isDebugEnabled()) {
//                	log.debug(String.format("unlikelyMatchString = %s", unlikelyMatchString));
//                	log.debug(String.format("found unlikelyCandidates = %s", REGEX.get("unlikelyCandidates").matcher(unlikelyMatchString).find()));
//                	log.debug(String.format("found okMaybeItsACandidate = %s",REGEX.get("okMaybeItsACandidate").matcher(unlikelyMatchString).find()));
//                }
                	
                if ((
                		REGEX.get("unlikelyCandidates").matcher(unlikelyMatchString).find() &&
                		REGEX.get("okMaybeItsACandidate").matcher(unlikelyMatchString).find() &&
                		!node.tagName().equalsIgnoreCase("BODY")
//                        unlikelyMatchString.search(REGEX.get("unlikelyCandidates")) !== -1 &&
//                        unlikelyMatchString.search(readability.regexps.okMaybeItsACandidate) === -1 &&
//                        node.tagName !== "BODY"
                    )
                ) {
//                    node.parentNode.removeChild(node);
//TODO confirm node.remove() is the same as above    
                    //TODO fix this
                    if (node.parent() != null) {
//                        if(log.isDebugEnabled()) log.debug(String.format("Removing unlikely candidate - %s", unlikelyMatchString));                    	
	                    node.remove(); 
//	                    nodeIndex-=1;
	                    continue;
                    }
                }               
            }

//            if (log.isDebugEnabled()) log.debug(String.format("node.tagName() = %s", node.tagName()));
            
            if (node.tagName().equalsIgnoreCase("P") || node.tagName().equalsIgnoreCase("TD") || node.tagName().equalsIgnoreCase("PRE")) {
            	nodesToScore.add(node);
//            	nodesToScore.set(nodesToScore.size(), node);
//            	nodesToScore[nodesToScore.length] = node;
            }

            /* Turn all divs that don't have children block level elements into p's */
            if (node.tagName().equalsIgnoreCase("DIV")) {
//                if (node.innerHTML.search(readability.regexps.divToPElements) === -1) {
            	
//            	if (log.isDebugEnabled()) log.debug(String.format("node.html() could not find match for divToPElements regex = %s", !REGEX.get("divToPElements").matcher(node.html()).find()));
            	
            	if(!REGEX.get("divToPElements").matcher(node.html()).find()) {
                    Element newNode = new Element(Tag.valueOf("p"), "");
                    newNode.html(node.html());
                                                            
//                    if (log.isDebugEnabled()) {
//                    	log.debug(String.format("replacing node %s with newNode %s", node, newNode));
//                    	log.debug(String.format("node.parent() = %s", node.parent()));
//                    }
                    //TODO fix this
                    if (node.parent() != null) {
//                    	if (log.isDebugEnabled()) log.debug(String.format("replacing %s with %s", node, newNode));
	                    node.replaceWith(newNode);
	//                    node.parentNode.replaceChild(newNode, node);
//	                    nodeIndex-=1;
	                    nodesToScore.add(newNode);
	//TODO figure out why this throws index out of bounds exception                    
	//                    nodesToScore.set(nodesToScore.size(), node);
	//                    nodesToScore[nodesToScore.length] = node;
                    }
                }
//                else
//                {
//                    /* EXPERIMENTAL */
//                	for(int i = 0; i < node.childNodes().size(); i++) {
//                    for(int i = 0, il = node.childNodes.length; i < il; i+=1) {
//                        Node childNode = node.childNode(i);
//TODO revisit
//                        if(childNode. === 3) { // Node.TEXT_NODE
//                            var p = document.createElement('p');
//                            p.innerHTML = childNode.nodeValue;
//                            p.style.display = 'inline';
//                            p.className = 'readability-styled';
//                            childNode.parentNode.replaceChild(p, childNode);
//                        }
//                    }
//                }
            } 
        }

//        if (log.isDebugEnabled()) log.debug(String.format("html after first pass = %s", page.html()));
        
//        if (log.isDebugEnabled()) log.debug(String.format("nodesToScore.size() = %s", nodesToScore.size()));
        
//        for (Element snode : nodesToScore) {
//        	if (log.isDebugEnabled()) log.debug(String.format("snode = %s", snode));        	
//        	if (log.isDebugEnabled()) log.debug(String.format("snode.parent() = %s", snode.parent()));
//        	
//        }
        
        /**
         * Loop through all paragraphs, and assign a score to them based on how content-y they look.
         * Then add their score to their parent node.
         *
         * A score is determined by things like number of commas, class names, etc. Maybe eventually link density.
        **/
        List<ScoredElement> candidates = new ArrayList<ScoredElement>();
        for (Element nodeToScore : nodesToScore) {
//        	if (log.isDebugEnabled()) {
//        		log.debug(String.format("nodeToScore = %s", nodeToScore));
//        		log.debug(String.format("nodeToScore.parent() = %s", nodeToScore.parent()));
//        	}
        	//TODO revisit if this is a good idea, may need to change how we create/initialize new ScoredElements
            ScoredElement parentNode = nodeToScore.parent() != null ? new ScoredElement(nodeToScore.parent(),-1) : null;
            ScoredElement grandParentNode = (parentNode != null && parentNode.parent() != null) ? new ScoredElement(parentNode.parent(),-1) : null;
            String innerText = nodeToScore.text();

//            if(log.isDebugEnabled()) log.debug(String.format("parentNode = %s", parentNode));
            
            if(parentNode == null || parentNode.tagName() == null) {
                continue;
            }

//            if(log.isDebugEnabled()) log.debug(String.format("innerText.length() = %s", innerText.length()));
            
            /* If this paragraph is less than 25 characters, don't even count it. */
            if(innerText.length() < 25) {
                continue; 
            }            

//            if (log.isDebugEnabled()) log.debug(String.format("parentNode.getScore() = %s", parentNode.getScore()));
            
            /* Initialize readability data for the parent. */
            if (parentNode.getScore() < 0) {
            	//TODO confirm if we need to use the returned Element instead                        	
            	initializeNode(parentNode);            	
            	candidates.add(parentNode);
            }
//            if(typeof parentNode.readability === 'undefined') {
//                readability.initializeNode(parentNode);
//                candidates.push(parentNode);
//            }

            /* Initialize readability data for the grandparent. */
            if (grandParentNode != null && grandParentNode.getScore() < 0) {
            	initializeNode(grandParentNode);
            	candidates.add(grandParentNode);
            }
//            if(grandParentNode && typeof(grandParentNode.readability) === 'undefined' && typeof(grandParentNode.tagName) !== 'undefined') {
//                readability.initializeNode(grandParentNode);
//                candidates.push(grandParentNode);
//            }

            int contentScore = 0;

            /* Add a point for the paragraph itself as a base. */
            contentScore+=1;

            /* Add points for any commas within this paragraph */
            contentScore += innerText.split(",").length;
            
            /* For every 100 characters in this paragraph, add another point. Up to 3 points. */
            contentScore += Math.min(Math.floor(innerText.length() / 100), 3);
            
            /* Add the score to the parent. The grandparent gets half. */
            parentNode.incrementScore(contentScore);

            if(grandParentNode != null) {
                grandParentNode.incrementScore(contentScore/2);             
            }
        }

        /**
         * After we've calculated scores, loop through all of the possible candidate nodes we found
         * and find the one with the highest score.
        **/
        ScoredElement topCandidate = null;
        for(ScoredElement candidate : candidates) {
            /**
             * Scale the final candidates score based on link density. Good content should have a
             * relatively small link density (5% or less) and be mostly unaffected by this operation.
            **/
        	
            candidate.setScore(candidate.getScore() * (1 - getLinkDensity(candidate)));
//            = candidates[c].readability.contentScore * (1-readability.getLinkDensity(candidates[c]));

//            if (log.isDebugEnabled()) log.debug(String.format("Candidate: %s (%s:%s) with score %s", candidate , candidate.className(), candidate.id(), candidate.getScore()));

            if(topCandidate == null || candidate.getScore() > topCandidate.getScore()) {
                topCandidate = candidate; 
            }
        }

        /**
         * If we still have no top candidate, just use the body as a last resort.
         * We also have to copy the body node so it is something we can modify.
         **/
        if (topCandidate == null || topCandidate.tagName().equalsIgnoreCase("BODY")) {
            topCandidate = new ScoredElement(Tag.valueOf("DIV"), "");
            topCandidate.html(page.html());
            page.html("");
            page.appendChild(topCandidate);
            initializeNode(topCandidate);
        }

        /**
         * Now that we have the top candidate, look through its siblings for content that might also be related.
         * Things like preambles, content split by ads that we removed, etc.
        **/
        Element articleContent = new Element(Tag.valueOf("DIV"), "");
        if (isPaging) {
            articleContent.attr("id", "readability-content");
        }
        float siblingScoreThreshold = (float)Math.max(10, topCandidate.getScore() * 0.2);
        //TODO verify Elements is ok here as original code used Nodes
//        if (log.isDebugEnabled()) log.debug(String.format("topCandidate = %s", topCandidate));
//        if (log.isDebugEnabled()) log.debug(String.format("topCandidate.parent() = %s", topCandidate.parent()));
        Elements siblingNodes = (topCandidate != null && topCandidate.parent() != null) ? topCandidate.siblingElements() : new Elements();
        
//        if (log.isDebugEnabled()) log.debug(String.format("siblingNodes.size() = %s", siblingNodes.size()));
//        
//        if (log.isDebugEnabled()) log.debug(String.format("html after scoring pass = %s", page.html()));
        
        for(int s=0; s < siblingNodes.size(); s++) {
//            Element siblingNode = siblingNodes.get(s);
        	ScoredElement siblingNode = new ScoredElement(siblingNodes.get(s), -1);
        	initializeNode(siblingNode);
            boolean append = false;

// TODO, need to figure out if we need to implement a ScoredElements object
//            if (log.isDebugEnabled()) log.debug(String.format("Looking at sibling node: %s (%s:%s) with score %s", siblingNode, siblingNode.className(), siblingNode.id(), siblingNode.getScore()));

//TODO this probably won't work given object matching in java
//            if (log.isDebugEnabled()) log.debug(String.format("siblingNode.html().equals(topCandidate.html()) = %s", siblingNode.html().equals(topCandidate.html())));
            if(siblingNode.html().equals(topCandidate.html())) {
                append = true;
            }

            float contentBonus = 0;
            /* Give a bonus if sibling nodes and top candidates have the example same classname */
//            if (log.isDebugEnabled()) log.debug(String.format("siblingNode.className().equals(topCandidate.className()) && !topCandidate.className().equals(\"\") = %s", siblingNode.className().equals(topCandidate.className()) && !topCandidate.className().equals("")));
            if(siblingNode.className().equals(topCandidate.className()) && !topCandidate.className().equals("")) {
                contentBonus += topCandidate.getScore() * 0.2;
            }
            //TODO check if the score we have for sibling node at this point is sufficient (only using intialize)
//            if (log.isDebugEnabled()) log.debug(String.format("(siblingNode.getScore() + contentBonus) >= siblingScoreThreshold = %s", (siblingNode.getScore() + contentBonus) >= siblingScoreThreshold));            
            if((siblingNode.getScore() + contentBonus) >= siblingScoreThreshold) {
                append = true;
            }
            
//            if (log.isDebugEnabled()) log.debug(String.format("siblingNode.tagName().equalsIgnoreCase(\"P\") = %s", siblingNode.tagName().equalsIgnoreCase("P")));
            if(siblingNode.tagName().equalsIgnoreCase("P")) {
                float linkDensity = getLinkDensity(siblingNode);
                String nodeContent = siblingNode.text();
                int nodeLength  = nodeContent.length();
                
                Pattern pattern = Pattern.compile("\\.( |$)");
                               
//                if (log.isDebugEnabled()) log.debug(String.format("nodeLength > 80 && linkDensity < 0.25 = %s", nodeLength > 80 && linkDensity < 0.25));
//                if (log.isDebugEnabled()) log.debug(String.format("nodeLength < 80 && linkDensity == 0 && pattern.matcher(nodeContent).find() = %s", nodeLength < 80 && linkDensity == 0 && pattern.matcher(nodeContent).find()));
                if(nodeLength > 80 && linkDensity < 0.25) {
                    append = true;
                }
                else if(nodeLength < 80 && linkDensity == 0 && pattern.matcher(nodeContent).find()) {
                    append = true;
                }
            }

            if(append) {
//                if(log.isDebugEnabled()) log.debug(String.format("Appending node: %s", siblingNode));

//TODO determine if this should be Element instead of Node                
                ScoredElement nodeToAppend = null;
                if(!siblingNode.tagName().equalsIgnoreCase("DIV") && !siblingNode.tagName().equalsIgnoreCase("P")) {
                    /* We have a node that isn't a common block level element, like a form or td tag. Turn it into a div so it doesn't get filtered out later by accident. */
                    
//                    if (log.isDebugEnabled()) log.debug(String.format("Altering siblingNode of %s  to div.", siblingNode.tagName()));
                    nodeToAppend = new ScoredElement(Tag.valueOf("DIV"), "");
//                    try {
                        nodeToAppend.attr("id", siblingNode.id());
                        nodeToAppend.html(siblingNode.html());
//                    }
//                    catch(er) {
//                        dbg("Could not alter siblingNode to div, probably an IE restriction, reverting back to original.");
//                        nodeToAppend = siblingNode;
//                        s-=1;
//                        sl-=1;
//                    }
                } else {
                    nodeToAppend = new ScoredElement(siblingNode, 0);
//TODO this looks wonky, modifying the length of the list within for loop
//                    s-=1;
//                    sl-=1;
                }
                
                /* To ensure a node does not interfere with readability styles, remove its classnames */
                nodeToAppend.classNames(new HashSet<String>());

                /* Append sibling and subtract from our list because it removes the node when you append to another node */
//TODO, not sure if this works the way comment suggests
                articleContent.appendChild(nodeToAppend);
            }
        }
        
//        if(log.isDebugEnabled()) log.debug(String.format("before prepArticle(articleContent) = %s", articleContent));
        
        /**
         * So we have all of the content that we need. Now we clean it up for presentation.
        **/
        prepArticle(articleContent);
        
//        if(log.isDebugEnabled()) log.debug(String.format("articleContent = %s", articleContent));

//        if (readability.curPageNum === 1) {
//            articleContent.innerHTML = '<div id="readability-page-1" class="page">' + articleContent.innerHTML + '</div>';
//        }

        /**
         * Now that we've gone through the full algorithm, check to see if we got any meaningful content.
         * If we didn't, we may need to re-run grabArticle with different flags set. This gives us a higher
         * likelihood of finding the content, and the sieve approach gives us a higher likelihood of
         * finding the -right- content.
        **/
//TODO uncomment
//        if(articleContent.text().length() < 250) {
//        	//reset page back - I think
//        	page.html(pageCacheHtml);
//
//            if (flagIsActive(FLAG_STRIP_UNLIKELYS)) {
//                removeFlag(FLAG_STRIP_UNLIKELYS);
//                return grabArticle(page);
//            }
//            else if (flagIsActive(FLAG_WEIGHT_CLASSES)) {
//                removeFlag(FLAG_WEIGHT_CLASSES);
//                return grabArticle(page);
//            }
//            else if (flagIsActive(FLAG_CLEAN_CONDITIONALLY)) {
//                removeFlag(FLAG_CLEAN_CONDITIONALLY);
//                return grabArticle(page);
//            } else {
//                return null;
//            }
//        }
        
        return articleContent;
    }
    
    /**
     * Get the density of links as a percentage of the content
     * This is the amount of text that is inside a link divided by the total text in the node.
     * 
     * @param Element
     * @return number (float)
    **/
    public static float getLinkDensity(Element e) {
        Elements links = e.getElementsByTag("a");
        int textLength = e.text().length();
        int linkLength = 0;
        for(Element link : links)
        {
            linkLength += link.text().length();
        }       

        return textLength != 0 ? linkLength / textLength : 0;
    }    
    
    /**
     * Prepare the article node for display. Clean out any inline styles,
     * iframes, forms, strip extraneous <p> tags, etc.
     *
     * @param Element
     * @return void
     **/
    public static void prepArticle(Element articleContent) {    	
        cleanStyles(articleContent);
//        if (log.isDebugEnabled()) log.debug(String.format("/***********************/ \nafter cleanStyles(articleContent) = %s", articleContent));
        killBreaks(articleContent);
//        if (log.isDebugEnabled()) log.debug(String.format("/***********************/ \nafter killBreaks(articleContent) = %s", articleContent));

        /* Clean out junk from the article content */
        cleanConditionally(articleContent, "form");
//        if (log.isDebugEnabled()) log.debug(String.format("/***********************/ \nafter cleanConditionally(articleContent, form) = %s", articleContent));
        clean(articleContent, "object");
//        if (log.isDebugEnabled()) log.debug(String.format("/***********************/ \nafter cleanConditionally(articleContent, object) = %s", articleContent));
        clean(articleContent, "h1");
//        if (log.isDebugEnabled()) log.debug(String.format("/***********************/ \nafter cleanConditionally(articleContent, h1) = %s", articleContent));

        /**
         * If there is only one h2, they are probably using it
         * as a header and not a subheader, so remove it since we already have a header.
        ***/
        if(articleContent.getElementsByTag("h2").size() == 1) {
            clean(articleContent, "h2");
//            if (log.isDebugEnabled()) log.debug(String.format("/***********************/ \nafter clean(articleContent, h2) = %s", articleContent));
        }
        clean(articleContent, "iframe");
//        if (log.isDebugEnabled()) log.debug(String.format("/***********************/ \nafter clean(articleContent, iframe) = %s", articleContent));

        cleanHeaders(articleContent);
//        if (log.isDebugEnabled()) log.debug(String.format("/***********************/ \nafter cleanHeaders(articleContent) = %s", articleContent));

        /* Do these last as the previous stuff may have removed junk that will affect these */
        cleanConditionally(articleContent, "table");
//        if (log.isDebugEnabled()) log.debug(String.format("/***********************/ \nafter cleanConditionally(articleContent, table) = %s", articleContent));
        cleanConditionally(articleContent, "ul");
//        if (log.isDebugEnabled()) log.debug(String.format("/***********************/ \nafter cleanConditionally(articleContent, ul) = %s", articleContent));
        cleanConditionally(articleContent, "div");
//        if (log.isDebugEnabled()) log.debug(String.format("/***********************/ \nafter cleanConditionally(articleContent, div) = %s", articleContent));

        /* Remove extra paragraphs */
        Elements articleParagraphs = articleContent.getElementsByTag("p");
        for(Element articleParagraph : articleParagraphs) {
            int imgCount    = articleParagraph.getElementsByTag("img").size();
            int embedCount  = articleParagraph.getElementsByTag("embed").size();
            int objectCount = articleParagraph.getElementsByTag("object").size();
            
            //TODO need to look at getInnerText method
//            if(imgCount == 0 && embedCount == 0 && objectCount == 0 /*&& readability.getInnerText(articleParagraphs[i], false) === ''*/) {
//                articleParagraphs[i].parentNode.removeChild(articleParagraphs[i]);
//            	articleParagraph.remove();
//            }
        }
//        if (log.isDebugEnabled()) log.debug(String.format("/***********************/ \nafter Removing extra paragraphs = %s", articleContent));
        
        Pattern replacePattern = Pattern.compile("<br[^>]*>\\s*<p", Pattern.CASE_INSENSITIVE);
        articleContent.html(replacePattern.matcher(articleContent.html()).replaceAll("<p"));      
//        if (log.isDebugEnabled()) log.debug(String.format("/***********************/ \nafter final replace = %s", articleContent));
    }    
    
    /**
     * Remove the style attribute on every e and under.
     * TODO: Test if getElementsByTagName(*) is faster.
     *
     * @param Element
     * @return void
    **/
    public static void cleanStyles(Element e) {
//        e = e || document;
//    	if (log.isDebugEnabled()) log.debug(String.format("in cleanStyles() e = %s", e));
//    	if (log.isDebugEnabled()) log.debug(String.format("e.children() = %s", e.children()));
        Element cur = (e.children() != null && !e.children().isEmpty()) ? e.child(0) : null;

        // Remove any root styles, if we're able.
//TODO revisit        
//        if(typeof e.removeAttribute === 'function' && e.className !== 'readability-styled') {
//            e.removeAttribute('style'); }

        // Go until there are no more child nodes
        while ( cur != null ) {
//            if ( cur.nodeType === 1 ) {
//                // Remove style attribute(s) :
//                if(cur.className !== "readability-styled") {
//                    cur.removeAttribute("style");                   
//                }
//                readability.cleanStyles( cur );
//            }
            cur = cur.nextElementSibling();
        }           
    }    
    
    /**
     * Remove extraneous break tags from a node.
     *
     * @param Element
     * @return void
     **/
    public static void killBreaks(Element e) {
    	e.html(REGEX.get("killBreaks").matcher(e.html()).replaceAll("<br />"));       
    }    
    
    /**
     * Clean an element of all tags of type "tag" if they look fishy.
     * "Fishy" is an algorithm based on content length, classnames, link density, number of images & embeds, etc.
     *
     * @return void
     **/
    //TODO understand what tag is used for
    public static void cleanConditionally(Element e, String tag) {

        if(!flagIsActive(FLAG_CLEAN_CONDITIONALLY)) {
            return;
        }

        Elements tagsList      = e.getElementsByTag(tag);
        int curTagsLength = tagsList.size();

        /**
         * Gather counts for other typical elements embedded within.
         * Traverse backwards so we can remove nodes at the same time without effecting the traversal.
         *
         * TODO: Consider taking into account original contentScore here.
        **/
        for (int i=curTagsLength-1; i >= 0; i--) {
            float weight = getClassWeight(tagsList.get(i));
//TODO revisit
            float contentScore = 0; 
//            	(typeof tagsList[i].readability !== 'undefined') ? tagsList[i].readability.contentScore : 0;
            
//            if (log.isDebugEnabled()) log.debug(String.format("Cleaning Conditionally %s (%s:%s) with score %s", tagsList.get(i), tagsList.get(i).className(), tagsList.get(i).id(), tagsList.get(i).getScore()));

            if(weight+contentScore < 0) {
                tagsList.get(i).remove();
            }
            else if (getCharCount(tagsList.get(i),",") < 10) {
                /**
                 * If there are not very many commas, and the number of
                 * non-paragraph elements is more than paragraphs or other ominous signs, remove the element.
                **/
                int p      = tagsList.get(i).getElementsByTag("p").size();
                int img    = tagsList.get(i).getElementsByTag("img").size();
                int li     = tagsList.get(i).getElementsByTag("li").size()-100;
                int input  = tagsList.get(i).getElementsByTag("input").size();

                int embedCount = 0;
                Elements embeds = tagsList.get(i).getElementsByTag("embed");
                for(Element embed : embeds) {                	
                	if (!REGEX.get("videos").matcher(embed.attr("src")).find()) {
                      embedCount+=1; 
                    }
                }

                float linkDensity   = getLinkDensity(tagsList.get(i));
                int contentLength = tagsList.get(i).text().length();
                boolean toRemove      = false;

                if ( img > p ) {
                    toRemove = true;
                } else if(li > p && !tag.equalsIgnoreCase("ul") && !tag.equalsIgnoreCase("ol")) {
                    toRemove = true;
                } else if( input > Math.floor(p/3) ) {
                    toRemove = true; 
                } else if(contentLength < 25 && (img == 0 || img > 2) ) {
                    toRemove = true;
                } else if(weight < 25 && linkDensity > 0.2) {
                    toRemove = true;
                } else if(weight >= 25 && linkDensity > 0.5) {
                    toRemove = true;
                } else if((embedCount == 1 && contentLength < 75) || embedCount > 1) {
                    toRemove = true;
                }

                if(toRemove) {
                	//TODO figure out if this null check is ok or if there is another way around this
                	if (tagsList.get(i).parent() != null) 
                		tagsList.get(i).remove();
                }
            }
        }
    }    
    
    /**
     * Clean a node of all elements of type "tag".
     * (Unless it's a youtube/vimeo video. People love movies.)
     *
     * @param Element
     * @param string tag to clean
     * @return void
     **/
    public static void clean(Element e, String tag) {
        Elements targetList = e.getElementsByTag(tag);
        boolean isEmbed = (tag.equalsIgnoreCase("object") || tag.equalsIgnoreCase("embed"));
        
        for (Element target : targetList) {
            /* Allow youtube and vimeo videos through as people usually want to see those. */
            if(isEmbed) {
                String attributeValues = "";
                for (Attribute attr : target.attributes()) {
                	attributeValues += attr.getValue() + "|";
                }                
                
                /* First, check the elements attributes to see if any of them contain youtube or vimeo */
                if (REGEX.get("videos").matcher(attributeValues).find()) {
                	continue;
                }

                /* Then check the elements inside this element for the same. */
                if (REGEX.get("videos").matcher(target.html()).find()) {
                	continue;
                }                
                
            }

            target.remove();
        }
    }    
    
    /**
     * Clean out spurious headers from an Element. Checks things like classnames and link density.
     *
     * @param Element
     * @return void
    **/
    public static void cleanHeaders(Element e) {
        for (int headerIndex = 1; headerIndex < 3; headerIndex+=1) {
            Elements headers = e.getElementsByTag("h" + headerIndex);
            for (int i=headers.size()-1; i >=0; i-=1) {
                if (getClassWeight(headers.get(i)) < 0 || getLinkDensity(headers.get(i)) > 0.33) {
                    headers.get(i).remove();
                }
            }
        }
    }    
    
    /**
     * Get the number of times a string s appears in the node e.
     *
     * @param Element
     * @param string - what to split on. Default is ","
     * @return number (integer)
    **/
    public static int getCharCount(Element e, String s) {
//        s = s || ",";
        return e.text().split(s).length-1;
    }    
    
    public static boolean flagIsActive(int flag) {
        	    	
    	for (int i : FLAGS) {
    		if (i == flag) return true;
    	}
    	
    	return false;
    }   

    public static void removeFlag(int flag) {
    	for (int i =0; i < FLAGS.length; i++) {
    		if (FLAGS[i] == flag) FLAGS[i] = 0;
    	}
    }        
    
    /**
     * Look for any paging links that may occur within the document.
     * 
     * @param body
     * @return object (array)
    **/
    public static String findNextPageLink(Element body, String url) /*: function (elem)*/ {

//    	var possiblePages = {},
//            allLinks = elem.getElementsByTagName('a'),
//            articleBaseUrl = readability.findBaseUrl();
    	
    	URL theUrl = null;
    	
    	try {
    		theUrl = new URL(url);
    	} catch (MalformedURLException e) {
    		if (log.isErrorEnabled()) log.error(null, e);
    	}    	
    	
    	List<Link> possiblePages = new ArrayList<Link>();
    	List<Element> allLinks = body.getElementsByTag("a");
    	String articleBaseUrl = findBaseUrl(url);
    	
        /**
         * Loop through all links, looking for hints that they may be next-page links.
         * Things like having "page" in their textContent, className or id, or being a child
         * of a node with a page-y className or id.
         *
         * Also possible: levenshtein distance? longest common subsequence?
         *
         * After we do that, assign each page a score, and 
        **/
        for(int i = 0; i < allLinks.size(); i++) {
            Element link = allLinks.get(i);
            //TODO vet this regex
            Link linkHref = new Link(allLinks.get(i).attr("href").replace("/#.*$", "").replace("/\\/$/", ""));
            
//            href.replace(/#.*$/, '').replace(/\/$/, '');

            /* If we've already seen this page, ignore it */
            //TODO revisit last part of this conditional statement
            if(linkHref.getHref().equals("") || linkHref.getHref().equals("articleBaseUrl") || linkHref.getHref().equals("url") /*|| linkHref in readability.parsedPages*/) {
                continue;
            }
            
            /* If it's on a different domain, skip it. */
            //TODO vet this regex
            if(theUrl.getHost() != linkHref.getHref().split("/\\/+/g")[1]) {
                continue;
            }
            
            String linkText = link.text();

            /* If the linkText looks like it's not the next page, skip it. */
            //TODO reinstate part 1 of this conditional
            if(/*linkText.match(readability.regexps.extraneous) ||*/ linkText.length() > 25) {
                continue;
            }

            /* If the leftovers of the URL after removing the base URL don't contain any digits, it's certainly not a next page link. */
            String linkHrefLeftover = linkHref.getHref().replace(articleBaseUrl, "");
            //TODO vet this regex
            if(!linkHrefLeftover.matches("/\\d/")) {
                continue;
            }
            
//TODO verify this is right            
            
            if(!(possiblePages.contains(linkHref))) {            	
            	//                possiblePages[linkHref] = {"score": 0, "linkText": linkText, "href": linkHref};             
            	possiblePages.add(new Link(linkHref.getHref(), linkText, 0));
            } else {
                possiblePages.set(possiblePages.indexOf(linkHref), new Link(linkHref.getHref(), linkHref.getText() + " | " + linkText, linkHref.getScore()));
            }


//TODO revist, does possiblePages need to be a map?
            Link linkObj = possiblePages.get(possiblePages.indexOf(linkHref));

            /**
             * If the articleBaseUrl isn't part of this URL, penalize this link. It could still be the link, but the odds are lower.
             * Example: http://www.actionscript.org/resources/articles/745/1/JavaScript-and-VBScript-Injection-in-ActionScript-3/Page1.html
            **/
            if(linkHref.getHref().indexOf(articleBaseUrl) != 0) {
                linkObj.setScore(linkObj.getScore() - 25);
            }

            String linkData = linkText + " " + link.className() + ' ' + link.id();
//TODO need to revisit this once global regex set up            
//            if(linkData.match(readability.regexps.nextLink)) {
//                linkObj.score += 50;
//            }
            if(linkData.matches("/pag(e|ing|inat)/i")) {
                linkObj.setScore(linkObj.getScore() + 25);
            }
            if(linkData.matches("/(first|last)/i")) { // -65 is enough to negate any bonuses gotten from a > or » in the text, 
                /* If we already matched on "next", last is probably fine. If we didn't, then it's bad. Penalize. */
//TODO need to revist once global regex set up
//                if(!linkObj.getText().matches(readability.regexps.nextLink)) {
//                    linkObj.setScore(linkObj.getScore() - 65);
//                }
            }
//            if(linkData.match(readability.regexps.negative) || linkData.match(readability.regexps.extraneous)) {
//                linkObj.score -= 50;
//            }
//            if(linkData.match(readability.regexps.prevLink)) {
//                linkObj.score -= 200;
//            }

            /* If a parentNode contains page or paging or paginat */
            Element parentNode = link.parent();
            boolean positiveNodeMatch = false;
            boolean negativeNodeMatch = false;

//TODO need to dig into this further            
//            while(parentNode) {
//                var parentNodeClassAndId = parentNode.className + ' ' + parentNode.id;
//                if(!positiveNodeMatch && parentNodeClassAndId && parentNodeClassAndId.match(/pag(e|ing|inat)/i)) {
//                    positiveNodeMatch = true;
//                    linkObj.score += 25;
//                }
//                if(!negativeNodeMatch && parentNodeClassAndId && parentNodeClassAndId.match(readability.regexps.negative)) {
//                    /* If this is just something like "footer", give it a negative. If it's something like "body-and-footer", leave it be. */
//                    if(!parentNodeClassAndId.match(readability.regexps.positive)) {
//                        linkObj.score -= 25;
//                        negativeNodeMatch = true;                       
//                    }
//                }
//                
//                parentNode = parentNode.parentNode;
//            }

            /**
             * If the URL looks like it has paging in it, add to the score.
             * Things like /page/2/, /pagenum/2, ?p=3, ?page=11, ?pagination=34
            **/
            if (linkHref.getHref().matches("/p(a|g|ag)?(e|ing|ination)?(=|\\/)[0-9]{1,2}/i") || linkHref.getHref().matches("/(page|paging)/i")) {
                linkObj.setScore(linkObj.getScore() + 25);
            }

            /* If the URL contains negative values, give a slight decrease. */
//TODO revisit once global regex is set up
//            if (linkHref.match(readability.regexps.extraneous)) {
//                linkObj.score -= 15;
//            }

            /**
             * Minor punishment to anything that doesn't match our current URL.
             * NOTE: I'm finding this to cause more harm than good where something is exactly 50 points.
             *       Dan, can you show me a counterexample where this is necessary?
             * if (linkHref.indexOf(window.location.href) !== 0) {
             *    linkObj.score -= 1;
             * }
            **/

            /**
             * If the link text can be parsed as a number, give it a minor bonus, with a slight
             * bias towards lower numbered pages. This is so that pages that might not have 'next'
             * in their text can still get scored, and sorted properly by score.
            **/
//TODO revisit
//            int linkTextAsNumber = parseInt(linkText, 10);
//            if(linkTextAsNumber) {
//                // Punish 1 since we're either already there, or it's probably before what we want anyways.
//                if (linkTextAsNumber === 1) {
//                    linkObj.score -= 10;
//                }
//                else {
//                    // Todo: Describe this better
//                    linkObj.score += Math.max(0, 10 - linkTextAsNumber);
//                }
//            }
        }

        /**
         * Loop thrugh all of our possible pages from above and find our top candidate for the next page URL.
         * Require at least a score of 50, which is a relatively high confidence that this page is the next link.
        **/
        Link topPage = null;
        for(Link page : possiblePages) {
//TODO revisit
//            if(possiblePages.hasOwnProperty(page)) {
//                if(possiblePages[page].score >= 50 && (!topPage || topPage.score < possiblePages[page].score)) {
//                    topPage = possiblePages[page];
//                }
//            }
        }

//        if(topPage) {
//            var nextHref = topPage.href.replace(/\/$/,'');
//
//            dbg('NEXT PAGE IS ' + nextHref);
//            readability.parsedPages[nextHref] = true;
//            return nextHref;            
//        }
//        else {
//            return null;
//        }
        
        return null;
    }	
    
    
    public static String findBaseUrl(String url) {
/*
    	var noUrlParams     = window.location.pathname.split("?")[0],
            urlSlashes      = noUrlParams.split("/").reverse(),
            cleanedSegments = [],
            possibleType    = "";
*/
    	//TODO parse full url out to protocol, host and path
    	
    	URL theUrl = null;
    	
    	try {
    		theUrl = new URL(url);
    	} catch (MalformedURLException e) {
    		if (log.isErrorEnabled()) log.error(null, e);
    	}
    	
    	String noUrlParams = theUrl.getPath().split("\\?")[0];
    	List<String> urlSlashes = (Arrays.asList(noUrlParams.split("/")));
    	Collections.reverse(urlSlashes);
    	List<String> cleanedSegments = new ArrayList<String>();
    	String possibleType = "";
    	
        for (int i = 0; i < urlSlashes.size(); i++) {
            String segment = urlSlashes.get(i);

            // Split off and save anything that looks like a file type.
            if (segment.indexOf(".") != -1) {
                possibleType = segment.split(".")[1];

                /* If the type isn't alpha-only, it's probably not actually a file extension. */
                //TODO vet this regex
                if(!possibleType.matches("/[^a-zA-Z]/")) {
                    segment = segment.split(".")[0];                    
                }
            }
            
            /**
             * EW-CMS specific segment replacement. Ugly.
             * Example: http://www.ew.com/ew/article/0,,20313460_20369436,00.html
            **/
            if(segment.indexOf(",00") != -1) {
                segment = segment.replace(",00", "");
            }

            // If our first or second segment has anything looking like a page number, remove it.
            //TODO vet this regex
            if (segment.matches("/((_|-)?p[a-z]*|(_|-))[0-9]{1,2}$/i") && ((i == 1) || (i == 0))) {
                segment = segment.replace("/((_|-)?p[a-z]*|(_|-))[0-9]{1,2}$/i", "");
            }


            boolean del = false;

            /* If this is purely a number, and it's the first or second segment, it's probably a page number. Remove it. */
            //TODO vet this regex
            if (i < 2 && segment.matches("/^\\d{1,2}$/")) {
                del = true;
            }
            
            /* If this is the first segment and it's just "index", remove it. */
            if(i == 0 && segment.toLowerCase() == "index") {
                del = true;
            }

            /* If our first or second segment is smaller than 3 characters, and the first segment was purely alphas, remove it. */
            if(i < 2 && segment.length() < 3 && !urlSlashes.get(0).matches("/[a-z]/i")) {
                del = true;
            }

            /* If it's not marked for deletion, push it to cleanedSegments. */
            if (!del) {
                cleanedSegments.add(segment);
            }
        }

        // This is our final, cleaned, base article URL.
//        return window.location.protocol + "//" + window.location.host + cleanedSegments.reverse().join("/");
        
        Collections.reverse(cleanedSegments);
        String cleanedSegmentsString = "";
        for (String seg : cleanedSegments)
        	cleanedSegmentsString = cleanedSegmentsString + seg + "/";
        
        return String.format("%s//%s%s", theUrl.getProtocol(), theUrl.getHost(), cleanedSegmentsString);
    }    

}
