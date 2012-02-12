
package com.casehistory.newsitempicker.guardian.feed;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class RefinementGroups{
   	private List<Refinements> refinements;
   	private String type;

 	public List<Refinements> getRefinements(){
		return this.refinements;
	}
	public void setRefinements(List<Refinements> refinements){
		this.refinements = refinements;
	}
 	public String getType(){
		return this.type;
	}
	public void setType(String type){
		this.type = type;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
