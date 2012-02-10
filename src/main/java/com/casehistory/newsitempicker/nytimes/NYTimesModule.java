/**
 * 
 */
package com.casehistory.newsitempicker.nytimes;

import java.util.List;

import org.apache.hadoop.fs.Path;
import org.apache.mahout.clustering.canopy.CanopyDriver;
import org.apache.mahout.common.distance.CosineDistanceMeasure;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;

/**
 * @author Abhinav Tripathi
 */
public class NYTimesModule {

	public static void main(String[] args) throws Exception {
		// get news articles for given query i.e. the args array here
		// NYTimesArticleSearcher searcher = new NYTimesArticleSearcher();
		// List<NYTimesArticle> articles = searcher.getNewsArticles(args);
		NYTimesSiteSearcher searcher = new NYTimesSiteSearcher();
		List<NYTimesArticle> articles = searcher.getNewsArticles(args);

		// write sparse vectors for these news articles
		int minDocFrequency = new Double(Math.ceil(articles.size() * 0.1d)).intValue();
		NYTimesDataVectorizer nyTimesDataVectorizer = new NYTimesDataVectorizer.NYTimesDataVectorizerBuilder()
				.inputPath("nyt-articles").outputPath("nyt-article-vectors").sequentialVectors().overwrite()
				.chunkSize(200).weighting("tfidf").analyzer("com.casehistory.newsitempicker.nytimes.MyAnalyzer")
				.minDocFrequency(minDocFrequency).nGram(2).normalize(2).minLogLikelihood(50).build();
		nyTimesDataVectorizer.buildVectors(articles);
		// analyzer("org.apache.lucene.analysis.WhitespaceAnalyzer")
		// minSupport(5)
		// minDocFrequency(3)
		// maxDocFrequency(90)
		// nGram(2)
		// minLogLikelihood(50)
		// normalize(2)

		Path vectorsFolder = new Path("nyt-article-vectors/tfidf-vectors");
		Path canopyCentroids = new Path("nyt-article-vectors/canopy-centroids");
		CanopyDriver.run(vectorsFolder, canopyCentroids, new CosineDistanceMeasure(), 0.2, 0.1, false, false);

		// run clustering algorithm on the generated vectors to obtain clusters
		NYTimesArticleClusterer nyTimesArticleClusterer = new NYTimesArticleClusterer.NYTimesArticleClustererBuilder()
				.inputPath("nyt-article-vectors/tfidf-vectors")
				.initialClusters("nyt-article-vectors/canopy-centroids/clusters-0-final")
				.outputClusters("nyt-articles-kmeans-cluster")
				.distanceMeasure("org.apache.mahout.common.distance.CosineDistanceMeasure").convergenceDelta(0.03)
				.maxIter(20).overwrite().clustering().build();
		nyTimesArticleClusterer.cluster();

		// print information about clusters, like top words
		NYTimesArticleClusterDumper nyTimesArticleClusterDumper = new NYTimesArticleClusterDumper.NYTimesArticleClusterDumperBuilder()
				.dictionaryType("sequencefile").dictionary("nyt-article-vectors/dictionary.file-*")
				.sequenceFileDir("nyt-articles-kmeans-cluster/clusters-1-final")
				.pointsDir("nyt-articles-kmeans-cluster/clusteredPoints").substring(10).numWords(10).build();
		nyTimesArticleClusterDumper.dumpInformation();
	}
}
