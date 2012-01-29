/**
 * 
 */
package com.casehistory.newsitempicker.nytimes;

import java.util.LinkedList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.mahout.vectorizer.SparseVectorsFromSequenceFiles;

import com.google.common.io.Closeables;

/**
 * @author A.Tripathi
 */
public class NYTimesDataVectorizer {

	private static Configuration conf;

	public static void buildVectors(List<NYTimesArticle> articles) throws Exception {
		conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		Path inputPath = new Path("nyt-articles");

		SequenceFile.Writer writer = new SequenceFile.Writer(fs, conf, inputPath, Text.class, Text.class);
		try {
			System.out.println("Writing SequenceFile for " + articles.size() + " news articles.");
			for (NYTimesArticle article : articles) {
				writer.append(new Text("Document::ID::" + article.url), new Text(article.text));
			}
		} finally {
			Closeables.closeQuietly(writer);
		}

		Path outputPath = new Path("nyt-article-vectors");
		List<String> argList = new LinkedList<String>();
		argList.add("-i");
		argList.add(inputPath.toString());
		argList.add("-o");
		argList.add(outputPath.toString());
		argList.add("-seq");
		String[] args = argList.toArray(new String[argList.size()]);

		SparseVectorsFromSequenceFiles.main(args);
	}

}
