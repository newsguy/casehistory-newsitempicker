/**
 * 
 */
package com.casehistory.newsitempicker.nytimes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.util.ReflectionUtils;
import org.apache.log4j.Logger;
import org.apache.mahout.clustering.WeightedVectorWritable;
import org.apache.mahout.utils.clustering.ClusterDumper;

/**
 * @author Abhinav Tripathi
 */
public class NYTimesArticleClusterDumper {

	private List<String> args = new ArrayList<String>();
	private final static Logger logger = Logger.getLogger(NYTimesArticleClusterer.class);

	private NYTimesArticleClusterDumper(List<String> arguments) {
		args = arguments;
	}

	public void dumpInformation() throws Exception {
		ClusterDumper.main(args.toArray(new String[args.size()]));
	}

	public static void main(String[] args) throws Exception {
		/*
		 * NYTimesArticleClusterDumper nyTimesArticleClusterDumper = new
		 * NYTimesArticleClusterDumper.NYTimesArticleClusterDumperBuilder()
		 * .dictionaryType
		 * ("sequencefile").dictionary("nyt-article-vectors/dictionary.file-*")
		 * .sequenceFileDir("nyt-articles-kmeans-cluster/clusters-1-final")
		 * .pointsDir
		 * ("nyt-articles-kmeans-cluster/clusteredPoints").substring(10
		 * ).numWords(20).build();
		 * nyTimesArticleClusterDumper.dumpInformation();
		 */
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		Path path = new Path("nyt-articles-kmeans-cluster/clusteredPoints/part-m-00000");
		List<ClusterEntry> clusterEntries = parseClusteredPoints(conf, fs, path);
		System.out.println();
		path = new Path("nyt-article-vectors/tfidf-vectors/part-r-00000");
		List<TFIDFVector> tfidfVectors = parseTFIDFVectors(conf, fs, path);
		mapUrlsToClusters(clusterEntries, tfidfVectors);
	}

	// TODO: Efficiency!
	private static void mapUrlsToClusters(List<ClusterEntry> clusterEntries, List<TFIDFVector> tfidfVectors) {
		for (TFIDFVector tfidfVector : tfidfVectors) {
			for (ClusterEntry clusterEntry : clusterEntries) {
				if (tfidfVector.belongsTo(clusterEntry)) {
					logger.info("The news article at " + tfidfVector.url + " belongs to cluster "
							+ clusterEntry.clusterId);
				}
			}
		}
	}

	private static List<ClusterEntry> parseClusteredPoints(Configuration conf, FileSystem fs, Path path)
			throws IOException {
		List<ClusterEntry> clusters = new ArrayList<ClusterEntry>();
		SequenceFile.Reader reader = null;
		try {
			reader = new SequenceFile.Reader(fs, path, conf);
			Writable key = (Writable) ReflectionUtils.newInstance(reader.getKeyClass(), conf);
			Writable value = (Writable) ReflectionUtils.newInstance(reader.getValueClass(), conf);
			while (reader.next(key, value)) {
				System.out.printf("%s\t%s\n", key, value);
				String valueStr = value.toString();
				String vecStr = valueStr.substring(valueStr.indexOf("["));
				StringTokenizer tokenizer = new StringTokenizer(vecStr.substring(1, vecStr.length() - 1), ", ");
				double[][] vector = new double[tokenizer.countTokens()][2];
				int i = 0;
				while (tokenizer.hasMoreTokens()) {
					String token = tokenizer.nextToken();
					vector[i][0] = Double.parseDouble(token.split(":")[0]);
					vector[i][1] = Double.parseDouble(token.split(":")[1]);
					i++;
				}
				clusters.add(new ClusterEntry(Integer.parseInt(key.toString()), vector));
			}
		} finally {
		}
		IOUtils.closeStream(reader);

		return clusters;
	}

	private static List<TFIDFVector> parseTFIDFVectors(Configuration conf, FileSystem fs, Path path) throws IOException {
		List<TFIDFVector> tfidfVectors = new ArrayList<TFIDFVector>();
		SequenceFile.Reader reader = null;
		try {
			reader = new SequenceFile.Reader(fs, path, conf);
			Writable key = (Writable) ReflectionUtils.newInstance(reader.getKeyClass(), conf);
			Writable value = (Writable) ReflectionUtils.newInstance(reader.getValueClass(), conf);
			while (reader.next(key, value)) {
				System.out.printf("%s\t%s\n", key, value);
				String valueStr = value.toString();
				String vecStr = valueStr.substring(valueStr.indexOf("{"));
				StringTokenizer tokenizer = new StringTokenizer(vecStr.substring(1, vecStr.length() - 1), ",");
				double[][] vector = new double[tokenizer.countTokens()][2];
				int i = 0;
				while (tokenizer.hasMoreTokens()) {
					String token = tokenizer.nextToken();
					vector[i][0] = Double.parseDouble(token.split(":")[0]);
					vector[i][1] = Double.parseDouble(token.split(":")[1]);
					i++;
				}
				tfidfVectors.add(new TFIDFVector(key.toString().split("::")[2], vector));
			}
		} finally {
		}
		IOUtils.closeStream(reader);

		return tfidfVectors;
	}

	public static class ClusterEntry {
		public final int clusterId;
		public final double[][] vector;

		public ClusterEntry(int clusterId, double[][] vector) {
			this.clusterId = clusterId;
			this.vector = new double[vector.length][2];
			for (int i = 0; i < vector.length; i++) {
				for (int j = 0; j < 2; j++) {
					this.vector[i][j] = vector[i][j];
				}
			}
		}
	}

	public static class TFIDFVector {
		public final String url;
		public final double[][] vector;

		public TFIDFVector(String url, double[][] vector) {
			this.url = url;
			this.vector = new double[vector.length][2];
			for (int i = 0; i < vector.length; i++) {
				for (int j = 0; j < 2; j++) {
					this.vector[i][j] = vector[i][j];
				}
			}
		}

		public boolean belongsTo(ClusterEntry clusterEntry) {
			boolean belongs = false;
			if (clusterEntry.vector.length == this.vector.length) {
				for (int i = 0; i < clusterEntry.vector.length; i++) {
					for (int j = 0; j < 2; j++) {
						if (clusterEntry.vector[i][j] == ((double) Math.round(this.vector[i][j] * 1000)) / 1000)
							belongs = true;
						else {
							belongs = false;
							break;
						}
					}
					if (!belongs)
						break;
				}
			}

			return belongs;
		}
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

		public NYTimesArticleClusterDumperBuilder pointsDir(String dir) {
			arguments.addAll(Arrays.asList(new String[] { "-p", dir }));
			return this;
		}

		public NYTimesArticleClusterDumper build() {
			return new NYTimesArticleClusterDumper(arguments);
		}
	}

}
