package simpleFindBugs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Scary Rank Bugs Sample
 */
public class ScaryRankBugs {
	private String checkStrValue1 = null;

	public ScaryRankBugs() {
		this.checkStrValue1 = "SomeValue";
	}

	String getCheckStrValue() {
		return this.checkStrValue1;
	}

	/**
	 * Detect when a return value is ignored.
	 */
	void exampleIgnoreReturnValue() {
		String someValue = getCheckStrValue();
		someValue.trim();

		if (someValue.equals(this.checkStrValue1)) {
			// Condition 1
		} else {
			// Condition 2
		}

		String bobStr = "bob";
		bobStr.replace('b', 'p');
		if (bobStr.equals("pop")) {
			// Condition 1
		} else {
			// Condition 2
		}
	}

	/**
	 * Detect a possible null pointer exception.
	 */
	void exampleNullPointer() {
		Map<String, String> aMap = new HashMap<String, String>();
		aMap.put("key1", "value1");
		String keyStr = aMap.get("some-key");
		if (keyStr != null) {
			int length = keyStr.length();
			System.out.println(length);
		}
		// It is possible that a null value be dereferenced.
		String lowerStr = keyStr.toLowerCase();
		System.out.println(lowerStr);
	}

	List<String> actions;

	/**
	 * Detect when a member filed is read before being initialized.
	 */
	void exampleFieldReadBeforeInitialized(String str) {
		StringTokenizer tokenizer = new StringTokenizer(str);
		while (tokenizer.hasMoreElements()) {
			actions.add(tokenizer.nextToken());
		}
	}
}