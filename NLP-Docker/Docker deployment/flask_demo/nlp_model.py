#!/usr/bin/env python
# coding: utf-8

# --- IMPORT LIBRARY
import matplotlib.pyplot as plt
import numpy as np
import pandas as pd

#get_ipython().run_line_magic('matplotlib', 'inline')

import nltk
import spacy
from nltk.corpus import stopwords
nltk.download('stopwords')


class nlp_model:

    def __init__(self, nlp=spacy.load("it_core_news_sm"), stopWords=set(stopwords.words('italian'))):
        self.nlp = nlp
        self.stopWords = stopWords

    # ---Tokenisation
    def return_token(self, sentence):
        doc = self.nlp(sentence)
        return [X.text for X in doc]

    def return_token_sent(self, sentence):
        doc = self.nlp(sentence)
        return [X.text for X in doc.sents]

    def clean_words(self, sentence):
        clean_words = []
        for token in self.return_token(sentence):
            if token not in self.stopWords:
                clean_words.append(token)
        return clean_words

    def return_stem(self, sentence):
        from nltk.stem.snowball import SnowballStemmer
        stemmer = SnowballStemmer(language='italian')
        doc = self.nlp(sentence)
        return [stemmer.stem(X.text) for X in doc]

    def stop_word_stem(self, sentence):
        clean_words = []
        for token in self.return_token(sentence):
            tokens = return_stem(token)
            for t in tokens:
                if t not in self.stopWords:
                    clean_words.append(t)
        return clean_words

    def cloudword(self, sentence):
        from wordcloud import WordCloud
        comment_words = ' '

        for val in sentence.split():
            val = str(val)
            tokens = val.split()

            # Converts each token into lowercase
            for i in range(len(tokens)):
                tokens[i] = tokens[i].lower()

            for words in tokens:
                comment_words += words + ' '

        wordcloud = WordCloud(width=800, height=800,
                              background_color='white',
                              stopwords=self.stopWords,
                              min_font_size=10).generate(comment_words)

        # plot the WordCloud image
        plt.figure(figsize=(8, 8), facecolor=None)
        plt.imshow(wordcloud)
        plt.axis("off")
        plt.tight_layout(pad=0)
        plt.savefig('static/cloudword.png')

        return plt

    def test(self):
        test = "Quel ramo del lago di Como, che volge a mezzogiorno, tra due catene non interrotte di monti, tutto a seni e a golfi, a seconda dello sporgere e del rientrare di quelli, vien, quasi a un tratto, a ristringersi, e a prender corso e figura di fiume, tra un promontorio a destra, e un’ampia costiera dall’altra parte; e il ponte, che ivi congiunge le due rive, par che renda ancor più sensibile all’occhio questa trasformazione, e segni il punto in cui il lago cessa, e l’Adda rincomincia, per ripigliar poi nome di lago dove le rive, allontanandosi di nuovo, lascian l’acqua distendersi e rallentarsi in nuovi golfi e in nuovi seni."

        print("---Tokenization:")
        print(self.return_token(test))

        print("---Clean words:")
        print(self.clean_words(test))

        print("---return_token_sent(test):")
        print(self.return_token_sent(test))

        print("---return_stem(test): ")
        print(self.return_stem(test))

        print("---cloudword(test): ")
        plt = self.cloudword(test)
        plt.show()
