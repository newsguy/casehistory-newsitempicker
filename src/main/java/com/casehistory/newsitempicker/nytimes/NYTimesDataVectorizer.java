/**
 * 
 */
package com.casehistory.newsitempicker.nytimes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.jms.IllegalStateException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.log4j.Logger;
import org.apache.mahout.vectorizer.SparseVectorsFromSequenceFiles;

import com.google.common.io.Closeables;

/**
 * @author Abhinav Tripathi
 */
public class NYTimesDataVectorizer {

	private List<String> args = new ArrayList<String>();
	private final Logger logger = Logger.getLogger(NYTimesDataVectorizer.class);

	private NYTimesDataVectorizer(List<String> arguments) {
		args = arguments;
	}

	public void buildVectors(List<NYTimesArticle> articles) throws Exception {
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		Path inputPath = new Path(findInputPathFromArgs());

		SequenceFile.Writer writer = new SequenceFile.Writer(fs, conf, inputPath, Text.class, Text.class);
		try {
			logger.info("Writing SequenceFile for " + articles.size() + " news articles ...");
			for (NYTimesArticle article : articles) {
				writer.append(new Text("Document::ID::" + article.url), new Text(article.text));
			}
		} finally {
			Closeables.closeQuietly(writer);
		}

		SparseVectorsFromSequenceFiles.main(args.toArray(new String[args.size()]));
		logger.info("Wrote SequenceFile for " + articles.size() + " news articles.");
	}

	private String findInputPathFromArgs() throws IllegalStateException {
		if (!args.contains("-i"))
			throw new IllegalStateException("NYTimesDataVectorizer not constructed properly!");

		return args.get(args.indexOf("-i") + 1);
	}

	public static class NYTimesDataVectorizerBuilder {
		private List<String> arguments = new ArrayList<String>();

		public NYTimesDataVectorizerBuilder inputPath(String inputPath) {
			arguments.addAll(Arrays.asList(new String[] { "-i", inputPath }));
			return this;
		}

		public NYTimesDataVectorizerBuilder outputPath(String outputPath) {
			arguments.addAll(Arrays.asList(new String[] { "-o", outputPath }));
			return this;
		}

		public NYTimesDataVectorizerBuilder sequentialVectors() {
			arguments.addAll(Arrays.asList(new String[] { "-seq" }));
			return this;
		}

		public NYTimesDataVectorizerBuilder overwrite() {
			arguments.addAll(Arrays.asList(new String[] { "-ow" }));
			return this;
		}

		public NYTimesDataVectorizerBuilder analyzer(String analyzer) {
			arguments.addAll(Arrays.asList(new String[] { "-a", analyzer }));
			return this;
		}

		public NYTimesDataVectorizerBuilder chunkSize(int chunkSize) {
			arguments.addAll(Arrays.asList(new String[] { "-chunk", chunkSize + "" }));
			return this;
		}

		public NYTimesDataVectorizerBuilder weighting(String format) {
			arguments.addAll(Arrays.asList(new String[] { "-wt", format }));
			return this;
		}

		public NYTimesDataVectorizerBuilder minSupport(int minSupport) {
			arguments.addAll(Arrays.asList(new String[] { "-s", minSupport + "" }));
			return this;
		}

		public NYTimesDataVectorizerBuilder minDocFrequency(int frequency) {
			arguments.addAll(Arrays.asList(new String[] { "-md", frequency + "" }));
			return this;
		}

		public NYTimesDataVectorizerBuilder maxDocFrequency(int frequency) {
			arguments.addAll(Arrays.asList(new String[] { "-x", frequency + "" }));
			return this;
		}

		public NYTimesDataVectorizerBuilder nGram(int num) {
			arguments.addAll(Arrays.asList(new String[] { "-ng", num + "" }));
			return this;
		}

		public NYTimesDataVectorizerBuilder minLogLikelihood(double ratio) {
			arguments.addAll(Arrays.asList(new String[] { "-ml", ratio + "" }));
			return this;
		}

		public NYTimesDataVectorizerBuilder normalize(double value) {
			arguments.addAll(Arrays.asList(new String[] { "-n", value + "" }));
			return this;
		}

		public NYTimesDataVectorizer build() {
			return new NYTimesDataVectorizer(arguments);
		}
	}

}
