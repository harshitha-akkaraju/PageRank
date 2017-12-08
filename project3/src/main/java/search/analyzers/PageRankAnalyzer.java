package search.analyzers;

import datastructures.concrete.ChainedHashSet;
import datastructures.concrete.KVPair;
import datastructures.concrete.dictionaries.ChainedHashDictionary;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IList;
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
		IDictionary<URI, Double> pgRanks = new ChainedHashDictionary<URI, Double>();
		for (KVPair<URI, ISet<URI>> page: graph) {
			pgRanks.put(page.getKey(), 1.0 / graph.size());
		}
		for (int i = 0; i < limit; i++) {
			IDictionary<URI, Double> tempPgRanks = new ChainedHashDictionary<URI, Double>();
			// give each page a new rank of 0.0
			for (KVPair<URI, ISet<URI>> uri: graph) {
				tempPgRanks.put(uri.getKey(), 0.0);
			}
			// share the node rank with every page it links to
			for (KVPair<URI, ISet<URI>> node: graph) {
				ISet<URI> edges = node.getValue();
				if (edges.size() > 0) {
					for (URI edge: edges) {
						double change = (decay * pgRanks.get(node.getKey())) / edges.size();
						double pgRank = tempPgRanks.get(edge) + change;
						tempPgRanks.put(edge, pgRank);
					}
				} else {
					for (KVPair<URI, Double> uri: tempPgRanks) {
						double change = (decay * pgRanks.get(node.getKey())) / graph.size();
						double pgRank = tempPgRanks.get(uri.getKey()) + change;
						tempPgRanks.put(uri.getKey(), pgRank);
					}
				}
			}
			boolean converge = true;
			for (KVPair<URI, Double> page: tempPgRanks) {
				URI uri = page.getKey();
				double pgRank = page.getValue() + ((1 - decay) / graph.size());
				// to make sure that we don't converge when a difference ends up being greater than epsilon
				if (converge) {
					// compare the difference between the old page rank and the new page rank
					converge = Math.abs(pgRanks.get(uri) - pgRank) < epsilon;
				}
				pgRanks.put(uri, pgRank);
			}
			if (converge) {
				return pgRanks;
			}	
		}
		return pgRanks;
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
