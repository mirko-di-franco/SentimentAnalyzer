package sentimentAnalyzer;

import java.util.Arrays;

public class SentimentAnalyzer {
	
	// Main method to detect opinions on feature sets in the review
	public static int[] detectProsAndCons(String review, String[][] featureSet, String[] posOpinionWords,
			String[] negOpinionWords) {
		int[] featureOpinions = new int[featureSet.length]; // output array to store opinions

        review = review.toLowerCase();
        
        // Iterate over each feature set
        nextFeature: for(int i=0; i < featureSet.length; i++){
            String[] features = featureSet[i];
            
            for(String feature : features){
            	// If the review contains the feature
                if(review.contains(feature)){
                	// Get the opinion on the feature
                    int opinion = getOpinionOnFeature(review, feature, posOpinionWords, negOpinionWords);
                    // If an opinion is found, store it and move to the next feature set
                    if(opinion != 0){
                        featureOpinions[i] = opinion;
                        continue nextFeature;
                    }
                }
            }
        }
 
		return featureOpinions; // Return the detected opinions
	}

	
	// Method to get the opinion on a feature
	private static int getOpinionOnFeature(String review, String feature, String[] posOpinionWords, String[] negOpinionWords) {
		// Try to find the opinion using the pattern "feature was opinion"
		int opinion = checkForWasPhrasePattern(review, feature, posOpinionWords, negOpinionWords);
		
		// If no opinion is found, try the pattern "opinion feature"
		if(opinion == 0){
		    opinion = checkForOpinionFirstPattern(review, feature, posOpinionWords, negOpinionWords);
		}
		
		return opinion; // Return the found opinion
		
	}	

	
	// Method to check the pattern "feature was opinion"
	private static int checkForWasPhrasePattern(String review, String feature, String[] posOpinionWords, String[] negOpinionWords) {
		int opinion = 0; // Initialize opinion to 0 (no opinion)
		String pattern = feature + " was "; // Create the pattern to search for
		
		int index = review.indexOf(pattern); // Find the index of the pattern in the review

		
		// Loop to find all occurrences of the pattern in the review
        while(index >= 0){
            String patternSuffix = review.substring(index + pattern.length()); // Extract the suffix after the pattern
            
            // Check if the suffix starts with a positive opinion word
            for(String opinionWord : posOpinionWords){
                if(patternSuffix.startsWith(opinionWord)){
                    return 1; // Return 1 for positive opinion
                }
            } 
            
            // Check if the suffix starts with a negative opinion word
            for(String opinionWord : negOpinionWords){
                if(patternSuffix.startsWith(opinionWord)){
                    return -1; // Return -1 for negative opinion
                }
            }
            
            // Find the next occurrence of the pattern in the review
            index = patternSuffix.indexOf(pattern);
            review = patternSuffix;
        }

		return opinion; // Return the opinion (0 if not found)
	}
	
	
	// Method to check the pattern "opinion feature"
	private static int checkForOpinionFirstPattern(String review, String feature, String[] posOpinionWords,
			String[] negOpinionWords) {
		String[] sentences = review.split("\\."); // Split the review into sentences
		int opinion = 0;

		// Loop through each sentence in the review
		for (String sentence : sentences) {
			int index = sentence.indexOf(feature); // Find the index of the feature in the sentence

			// If the feature is found in the sentence
			if (index > 0) {
				String patternPrefix = sentence.substring(0, index).trim(); // Extract the prefix before the feature

				// Check if the prefix ends with a positive opinion word
				for (String opinionWord : posOpinionWords) {

					if (patternPrefix.endsWith(opinionWord)) {
						return 1;
					}
				}

				// Check if the prefix ends with a negative opinion word
				for (String opinionWord : negOpinionWords) {

					if (patternPrefix.endsWith(opinionWord)) {
						return -1;
					}
				}

			}
		}

		return opinion; // Return the opinion (0 if not found)
	}

	
	
	
	/*MAIN */
	public static void main(String[] args) {
		
		// Example review
		String review = "Haven't been here in years! Fantastic service and the food was delicious! Definetly will be a frequent flyer! Francisco was very attentive";
		
		//String review = "Sorry OG, but you just lost some loyal customers. Horrible service, no smile or greeting just attitude. The breadsticks were stale and burnt, appetizer was cold and the food came out before the salad.";
		
		// Definition of the feature set
		String[][] featureSet = { 
		        { "ambiance", "ambience", "atmosphere", "decor" },
				{ "dessert", "ice cream", "desert" }, 
				{ "food" }, 
				{ "soup" },
				{ "service", "management", "waiter", "waitress", "bartender", "staff", "server" } };
		
		// Positive opinion words
		String[] posOpinionWords = { "good", "fantastic", "friendly", "great", "excellent", "amazing", "awesome",
				"delicious" };
		
		// Negative opinion words
		String[] negOpinionWords = { "slow", "bad", "horrible", "awful", "unprofessional", "poor" };

	
		int[] featureOpinions = detectProsAndCons(review, featureSet, posOpinionWords, negOpinionWords);
		// Print the detected opinions
		System.out.println("Opinions on Features: " + Arrays.toString(featureOpinions));
	}
}