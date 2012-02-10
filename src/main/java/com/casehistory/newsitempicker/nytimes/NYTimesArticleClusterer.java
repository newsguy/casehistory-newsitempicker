/**
 * 
 */
package com.casehistory.newsitempicker.nytimes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.mahout.clustering.kmeans.KMeansDriver;

/**
 * @author abhinav
 * 
 */
public class NYTimesArticleClusterer {

	private List<String> args = new ArrayList<String>();
	private final Logger logger = Logger.getLogger(NYTimesArticleClusterer.class);

	private NYTimesArticleClusterer(List<String> arguments) {
		args = arguments;
	}

	public void cluster() throws Exception {
		logger.info("Using K-means clustering algorithm to cluster the news articles ...");
		KMeansDriver.main(args.toArray(new String[args.size()]));
		logger.info("Finished clustering of news articles.");
	}

	public static class NYTimesArticleClustererBuilder {
		private List<String> arguments = new ArrayList<String>();

		public NYTimesArticleClustererBuilder clustering() {
			arguments.addAll(Arrays.asList(new String[] { "-cl" }));
			return this;
		}

		public NYTimesArticleClustererBuilder maxIter(int x) {
			arguments.addAll(Arrays.asList(new String[] { "-x", x + "" }));
			return this;
		}

		public NYTimesArticleClustererBuilder numClusters(int num) {
			arguments.addAll(Arrays.asList(new String[] { "-k", num + "" }));
			return this;
		}

		public NYTimesArticleClustererBuilder convergenceDelta(double delta) {
			arguments.addAll(Arrays.asList(new String[] { "-cd", delta + "" }));
			return this;
		}

		public NYTimesArticleClustererBuilder distanceMeasure(String distanceMeasure) {
			arguments.addAll(Arrays.asList(new String[] { "-dm", distanceMeasure }));
			return this;
		}

		public NYTimesArticleClustererBuilder outputClusters(String outputClustersPath) {
			arguments.addAll(Arrays.asList(new String[] { "-o", outputClustersPath }));
			return this;
		}

		public NYTimesArticleClustererBuilder initialClusters(String initialClustersPath) {
			arguments.addAll(Arrays.asList(new String[] { "-c", initialClustersPath }));
			return this;
		}

		public NYTimesArticleClustererBuilder inputPath(String inputPath) {
			arguments.addAll(Arrays.asList(new String[] { "-i", inputPath }));
			return this;
		}

		public NYTimesArticleClustererBuilder overwrite() {
			arguments.addAll(Arrays.asList(new String[] { "-ow" }));
			return this;
		}

		public NYTimesArticleClusterer build() {
			return new NYTimesArticleClusterer(arguments);
		}
	}

}
