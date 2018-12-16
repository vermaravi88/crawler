package com.rv.crawler;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Web Crawler Class
 * 
 * @author rv
 * 
 */
public class CrawlerMain {

	private Set<String> internalLinks = new HashSet<String>();
	private Set<String> externalLinks = new HashSet<String>();
	private Set<String> staticLinks = new HashSet<String>();

	public static void main(String[] args) {
		CrawlerMain test = new CrawlerMain();
		final String url = "https://en.wikipedia.org/";
		test.getLinks(url);
		test.getExternalLinks(url);
		test.getStaticLinks(url);
		
		System.out.println("---------------INTERNAL LINKS---------------------------");
		System.out.println("Found (" + test.internalLinks.size() + ") internal links");
		for(String link : test.internalLinks)
			System.out.println(link);
		System.out.println("========================================================");
		System.out.println("---------------EXTERNAL LINKS---------------------------");
		System.out.println("Found (" + test.externalLinks.size() + ") external links");
		for(String link : test.externalLinks)
			System.out.println(link);
		System.out.println("========================================================");
		System.out.println("---------------STATIC LINKS-----------------------------");
		System.out.println("Found (" + test.staticLinks.size() + ") static links");
		for(String link : test.staticLinks)
			System.out.println(link);
		System.out.println("========================================================");
	}

	/**
	 * Crawls and fetch internal links
	 * 
	 * @param url
	 *            url to be crawled
	 * @return Set links and link text
	 */
	public Set<String> getLinks(final String url) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			System.out.println("Exception occurred- " + e.getMessage());
		}
		Elements linksOnPage = doc.select("a[href]");
		System.out.println("Found (" + linksOnPage.size() + ") links");
		for (Element link : linksOnPage) {
			final String linkStr = link.absUrl("href");
			if (linkStr.startsWith(url)) {
				internalLinks.add(linkStr);
			} else {
				externalLinks.add(linkStr);
			}
		}
		return externalLinks;
	}

	/**
	 * Crawls and fetch external links
	 * 
	 * @param url
	 *            url to be crawled
	 * @return Set links and link text
	 */
	public Set<String> getExternalLinks(final String url) {
		if (externalLinks.isEmpty()) {
			getLinks(url);
		}
		return externalLinks;
	}

	/**
	 * Crawls and fetch static links of static content such as images
	 * 
	 * @param url
	 *            url to be crawled
	 * @return Set links and link text
	 */
	public Set<String> getStaticLinks(final String url) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			System.out.println("Exception occurred- " + e.getMessage());
		}
		Elements media = doc.select("[src]");
		Elements imports = doc.select("link[href]");
		System.out.println("Found (" + media.size() + ") static images links");
		for (Element link : media) {
			staticLinks.add(link.absUrl("src"));
		}
		System.out.println("Found (" + imports.size() + ") static links");
		for (Element link : imports) {
			staticLinks.add(link.absUrl("href"));
		}
		return staticLinks;
	}

}