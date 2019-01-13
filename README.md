# TwiNER
Twitter Named Entity Recogniser, a named entity recognizer which can recognize named entities(eg.person, place or organization) from tweets by segmenting tweets and then exploiting the contextual information of tweets.

## TwiNER vs Traditional NERs 
Traditional NER methods such as Stanford NER and DBPedia Spotlight depend on local linguistic features such as
capitalization part-of-speech (POS) tags.

However, tweets are usually informal in nature and short containing grammatical errors, misspellings and unreliable capitalizations.

TwiNER uses Segmentation algorithm, which segements tweets into candidate entities based on occurrences of the words in the tweet dataset and contextual information, matching the rest of the tweet text with the DBPedia database to find real named entities.

