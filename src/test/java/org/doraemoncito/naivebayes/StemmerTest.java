package org.doraemoncito.naivebayes;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StemmerTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(StemmerTest.class);

	private String[] toArray(String... filepaths) {
		return filepaths;
	}

	/**
	 * Test suite for demonstrating the Stemmer. It reads text from a a list of files, stems each word, and writes the
	 * result to standard output. Note that the word stemmed is expected to be in lower case: forcing lower case must be
	 * done outside the Stemmer class. Usage: Stemmer file-name file-name ...
	 * 
	 * @return
	 */
	public List<String> stemmerHelper(String[] filepaths) {
		List<String> result = new ArrayList<>();
		char[] w = new char[501];
		Stemmer s = new Stemmer();
		for (int i = 0; i < filepaths.length; i++) {
			try (FileInputStream in = new FileInputStream(filepaths[i])) {

				try {
					while (true) {
						int ch = in.read();
						if (Character.isLetter((char) ch)) {
							int j = 0;
							while (true) {
								ch = Character.toLowerCase((char) ch);
								w[j] = (char) ch;
								if (j < 500)
									j++;
								ch = in.read();
								if (!Character.isLetter((char) ch)) {
									/* to test add(char ch) */
									for (int c = 0; c < j; c++)
										s.add(w[c]);

									/* or, to test add(char[] w, int j) */
									/* s.add(w, j); */

									s.stem();
									{
										String u;

										/* and now, to test toString() : */
										u = s.toString();

										/* to test getResultBuffer(), getResultLength() : */
										/* u = new String(s.getResultBuffer(), 0, s.getResultLength()); */

										result.add(u);
									}
									break;
								}
							}
						}
						if (ch < 0)
							break;
						System.out.print((char) ch); //XXX
					}
				} catch (IOException e) {
					LOGGER.error("error reading {}", filepaths[i], e);
					throw new RuntimeException(e);
				}
			} catch (FileNotFoundException e) {
				LOGGER.error("file {} not found", filepaths[i], e);
				throw new IllegalArgumentException(e);
			} catch (IOException e) {
				LOGGER.error("IO exception whilst stemming", e);
				throw new RuntimeException(e);
			}
		}
		return result;
	}
	
	private String getResourcePath(String resourceName) {
		try {
			URL resourceUrl = getClass().getResource(resourceName);
			Path resourcePath = Paths.get(resourceUrl.toURI());
			return resourcePath.toAbsolutePath().toString();
		} catch (URISyntaxException | NullPointerException e) {
			LOGGER.error("Resource '{}' could not be found in the classpath", resourceName, e);
			throw new IllegalArgumentException(String.format("Resource '%s' could not be found in the classpath", resourceName), e);
		}
	}

	@Ignore
	@Test
	public void testStemmer() {
		List<String> actualResult = stemmerHelper(toArray(getResourcePath("stemmer/stemmer_voc.txt")));
		assertNotNull(actualResult);
		fail("TODO: need to compare the result to the content of the stemmer_output.txt file");
	}

}
