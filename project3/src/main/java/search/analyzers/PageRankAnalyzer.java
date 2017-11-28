package search.analyzers;

import datastructures.concrete.ChainedHashSet;
import datastructures.concrete.KVPair;
import datastructures.concrete.dictionaries.ChainedHashDictionary;
import datastructures.interfaces.IDictionary;
<<<<<<< HEAD
import datastructures.concrete.dictionaries.ChainedHashDictionary;
import datastructures.concrete.ChainedHashSet;
=======
import datastructures.interfaces.IList;
>>>>>>> c9f067a65f17e7b51e570ec9fb30de49cc11a6d2
import datastructures.interfaces.ISet;
import misc.exceptions.NotYetImplementedException;
import search.models.Webpage;

import java.net.URI;

/**
 * This class is responsible for computing the 'page rank' of all available webpages.
 * If a webpage has many different links to it, it should have a higher page rank.
 * See the spec for more details.
 */
public class PageRankAnalyzer {
	private IDictionary<URI, Double> pageRanks;

	/**
	 * Computes a graph representing the internet and computes the page rank of all
	 * available webpages.
	 *
	 * @param webpages  A set of all webpages we have parsed.
	 * @param decay     Represents the "decay" factor when computing page rank (see spec).
	 * @param epsilon   When the difference in page ranks is less then or equal to this number,
	 *                  stop iterating.
	 * @param limit     The maximum number of iterations we spend computing page rank. This value
	 *                  is meant as a safety valve to prevent us from infinite looping in case our
	 *                  page rank never converges.
	 */
	public PageRankAnalyzer(ISet<Webpage> webpages, double decay, double epsilon, int limit) {
		// Implementation note: We have commented these method calls out so your
		// search engine doesn't immediately crash when you try running it for the
		// first time.
		//
		// You should uncomment these lines when you're ready to begin working
		// on this class.

		// Step 1: Make a graph representing the 'internet'
		IDictionary<URI, ISet<URI>> graph = this.makeGraph(webpages);

		// Step 2: Use this graph to compute the page rank for each webpage
		this.pageRanks = this.makePageRanks(graph, decay, limit, epsilon);

		// Note: we don't store the graph as a field: once we've computed the
		// page ranks, we no longer need it!
	}
	
    private IDictionary<URI, ISet<URI>> makeGraph(ISet<Webpage> webpages) {
		IDictionary<URI, ISet<URI>> result = new ChainedHashDictionary<URI, ISet<URI>>();
		for (Webpage page: webpages) {
			result.put(page.getUri(), null);
		}
		for (Webpage page: webpages) {
			URI uri = page.getUri();
			IList<URI> links = page.getLinks();
			ISet<URI> edges = new ChainedHashSet<URI>();
			for (URI link: links) {
				if (result.containsKey(link) && !uri.equals(link)) {
					edges.add(link);
				}
			}
			result.put(uri, edges);
		}
    return result;
}



	/**
	 * Computes the page ranks for all webpages in the graph.
	 *
	 * Precondition: assumes 'this.graphs' has previously been initialized.
	 *
	 * @param decay     Represents the "decay" factor when computing page rank (see spec).
	 * @param epsilon   When the difference in page ranks is less then or equal to this number,
	 *                  stop iterating.
	 * @param limit     The maximum number of iterations we spend computing page rank. This value
	 *                  is meant as a safety valve to prevent us from infinite looping in case our
	 *                  page rank never converges.
	 */
	private IDictionary<URI, Double> makePageRanks(IDictionary<URI, ISet<URI>> graph,
			double decay,
			int limit,
			double epsilon) {
		// Step 1: The initialize step should go here
		IDictionary<URI, Double> pageRanks = new ChainedHashDictionary<URI, Double>();
		for (KVPair<URI, ISet<URI>> pair: graph) {
			pageRanks.put(pair.getKey(), 1.0 / graph.size());
		}
		boolean converge = true;
		for (int i = 0; i < limit; i++) {
			// Step 2: The update step should go here
			for (KVPair<URI, ISet<URI>> pair: graph) {
				double pageRank = pageRanks.get(pair.getKey());
				double newPageRank = 0.0;
				ISet<URI> edges = pair.getValue();
				if (edges.size() > 0) {
					for (URI edge: edges) {
						double oldPageRank = pageRanks.get(edge);
						double incrementValue = decay * ((double) oldPageRank / graph.get(edge).size());
						newPageRank += incrementValue;
					}
				} else {
					double oldPageRank = pageRanks.get(pair.getKey());
					double incrementValue = decay * ((double) oldPageRank / graph.size());
					newPageRank += incrementValue;
					for (KVPair<URI, ISet<URI>> node: graph) {
						if (!node.getKey().equals(pair.getKey())) {
							pageRanks.put(node.getKey(), pageRanks.get(node.getKey()) + newPageRank);
						}
					}
				}
				newPageRank += ((1 - decay) / graph.size());
				pageRanks.put(pair.getKey(), newPageRank);
				if (converge) {
					converge = Math.abs(pageRank - newPageRank) < epsilon;
				}
			}
			// Step 3: the convergence step should go here.
			// Return early if we've converged.
			if (converge) {
				return pageRanks;
			}
		}
		return pageRanks;
	}

	/**
	 * Returns the page rank of the given URI.
	 *
	 * Precondition: the given uri must have been one of the uris within the list of
	 *               webpages given to the constructor.
	 */
	public double computePageRank(URI pageUri) {
		// Implementation note: this method should be very simple: just one line!
		// TODO: Add working code here
		return this.pageRanks.get(pageUri);
	}
}
