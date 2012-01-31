/**
 * 
 */
package com.casehistory.newsitempicker.nytimes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.mahout.utils.clustering.ClusterDumper;

/**
 * @author Abhinav Tripathi
 */
public class NYTimesArticleClusterDumper {

	private List<String> args = new ArrayList<String>();
	private final Logger logger = Logger.getLogger(NYTimesArticleClusterer.class);

	private NYTimesArticleClusterDumper(List<String> arguments) {
		args = arguments;
	}

	public void dumpInformation() throws Exception {
		ClusterDumper.main(args.toArray(new String[args.size()]));
	}

	public static class NYTimesArticleClusterDumperBuilder {
		private List<String> arguments = new ArrayList<String>();

		public NYTimesArticleClusterDumperBuilder dictionaryType(String type) {
			arguments.addAll(Arrays.asList(new String[] { "-dt", type }));
			return this;
		}

		public NYTimesArticleClusterDumperBuilder dictionary(String dictionary) {
			arguments.addAll(Arrays.asList(new String[] { "-d", dictionary }));
			return this;
		}

		public NYTimesArticleClusterDumperBuilder sequenceFileDir(String sequenceFileDir) {
			arguments.addAll(Arrays.asList(new String[] { "-s", sequenceFileDir }));
			return this;
		}

		public NYTimesArticleClusterDumperBuilder substring(int numChars) {
			arguments.addAll(Arrays.asList(new String[] { "-b", numChars + "" }));
			return this;
		}

		public NYTimesArticleClusterDumperBuilder numWords(int num) {
			arguments.addAll(Arrays.asList(new String[] { "-n", num + "" }));
			return this;
		}

		public NYTimesArticleClusterDumper build() {
			return new NYTimesArticleClusterDumper(arguments);
		}
	}

}
