# Sentiment Analyzer
This Java project analyzes reviews and determines the sentiment (positive or negative) associated with specific features mentioned in the review. This can be particularly useful for businesses looking to understand customer feedback on various aspects such as service, food, ambiance, etc.

## Features
- Detects opinions on predefined features in a review.
- Identifies positive and negative sentiments associated with each feature.

## Code Structure
The project includes the following main methods:
1. **`detectProsAndCons(String review, String[][] featureSet, String[] posOpinionWords, String[] negOpinionWords)`**: This method detects opinions on feature sets in the review. It converts the review to lowercase, iterates over each feature set, and checks if they are present in the review. It calls ***getOpinionOnFeature*** to get the opinion on the found feature.
2. **`getOpinionOnFeature(String review, String feature, String[] posOpinionWords, String[] negOpinionWords)`**: This method gets the opinion on a feature by attempting to find the opinion using two methods: ***checkForWasPhrasePattern*** and ***checkForOpinionFirstPattern***.
3. **`checkForWasPhrasePattern(String review, String feature, String[] posOpinionWords, String[] negOpinionWords)`**: This method checks the pattern "feature was opinion" in the review. It returns 1 for positive opinion, -1 for negative opinion, and 0 if nothing is found.
4. **`checkForOpinionFirstPattern(String review, String feature, String[] posOpinionWords, String[] negOpinionWords)`**: This method checks the pattern "opinion feature" in the review. It splits the review into sentences and checks each sentence. It returns 1 for positive opinion, -1 for negative opinion, and 0 if nothing is found.
