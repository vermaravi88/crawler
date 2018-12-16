package com.rv.crawler;

import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.rv.crawler.CrawlerMain;

public class CrawlerTest {
	
	private CrawlerMain test;
	String TEST_URL = "https://en.wikipedia.org/";

	@Before
	public void init() {
		test = new CrawlerMain();
	}

	@Test
	public void validInternalLinksTest() {
		Set<String> links = test.getLinks(TEST_URL);
		//there must have something
		Assert.assertTrue(links.size() != 0);
	}
	
	@Test
	public void validExternalLinksTest() {
		Set<String> links = test.getExternalLinks(TEST_URL);
		//there must have something
		Assert.assertTrue(links.size() != 0);
	}
	
	@Test
	public void validStaticLinksTest() {
		Set<String> links = test.getStaticLinks(TEST_URL);
		//there must have something
		Assert.assertTrue(links.size() != 0);
	}

}
