/**
 * 
 */
package com.casehistory.newsitempicker.nytimes;

import java.util.List;

/**
 * @author Abhinav Tripathi
 */
public class NYTimesModule {

	public static void main(String[] args) throws Exception {
		// get news articles for given query i.e. the args array here
		NYTimesArticleSearcher searcher = new NYTimesArticleSearcher();
		List<NYTimesArticle> articles = searcher.getNewsArticles(args);

		// write sparse vectors for these news articles
		NYTimesDataVectorizer nyTimesDataVectorizer = new NYTimesDataVectorizer.NYTimesDataVectorizerBuilder()
				.inputPath("nyt-articles").outputPath("nyt-article-vectors").sequentialVectors().overwrite()
				.chunkSize(200).weighting("tfidf").build();
		nyTimesDataVectorizer.buildVectors(articles);
		// analyzer("org.apache.lucene.analysis.WhitespaceAnalyzer")
		// minSupport(5)
		// minDocFrequency(3)
		// maxDocFrequency(90)
		// nGram(2)
		// minLogLikelihood(50)
		// normalize(2)

		// run clustering algorithm on the generated vectors to obtain clusters
		NYTimesArticleClusterer nyTimesArticleClusterer = new NYTimesArticleClusterer.NYTimesArticleClustererBuilder()
				.inputPath("nyt-article-vectors/tfidf-vectors").intialClusters("nyt-articles-intial-cluster")
				.outputClusters("nyt-articles-kmeans-cluster")
				.distanceMeasure("org.apache.mahout.common.distance.SquaredEuclideanDistanceMeasure")
				.convergenceDelta(1.0).numClusters(2).maxIter(20).clustering().build();
		nyTimesArticleClusterer.cluster();

		// print information about clusters, like top words
		NYTimesArticleClusterDumper nyTimesArticleClusterDumper = new NYTimesArticleClusterDumper.NYTimesArticleClusterDumperBuilder()
				.dictionaryType("sequencefile").dictionary("nyt-article-vectors/dictionary.file-*")
				.sequenceFileDir("nyt-articles-kmeans-cluster/clusters-1-final").substring(10).numWords(10).build();
		nyTimesArticleClusterDumper.dumpInformation();
	}
}
