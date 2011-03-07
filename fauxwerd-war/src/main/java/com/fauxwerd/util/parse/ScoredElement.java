package com.fauxwerd.util.parse;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScoredElement extends Element {
	
	private float score = -1;
	private ScoredElement scoredParentNode;
		
	public ScoredElement(Tag tag, String baseUri) {
		super(tag, baseUri);
		// TODO Auto-generated constructor stub
	}
	
	public ScoredElement(Element element, float score) {		
		super(element.tag(), element.baseUri());
		this.html(element.html());		
		this.score = score;
		this.setParentNode(element.parent());
	}
	    
    public final ScoredElement scoredParent() {
    	if (scoredParentNode == null) {
    		scoredParentNode = new ScoredElement(super.parent(), -1);
    	}
        return scoredParentNode;
    }
	

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}
	
	public void incrementScore(float inc) {
		if (inc != 0) {
			score += inc;
		}
		else {
			score += 1;
		}
	}
	
}
