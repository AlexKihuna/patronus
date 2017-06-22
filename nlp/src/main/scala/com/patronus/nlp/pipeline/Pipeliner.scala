package com.patronus.nlp.tag

import java.util.Properties

import scala.collection.JavaConverters._

import edu.stanford.nlp.ling.CoreAnnotations._
import edu.stanford.nlp.pipeline._
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations._
import edu.stanford.nlp.trees.TreeCoreAnnotations._
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations._

object Pipeliner extends App {
  val props = new Properties()
//  props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref")
//  props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse")
  props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, sentiment")
  val pipeline = new StanfordCoreNLP(props)
//  val text = "Stanford CoreNLP provides a set of natural language analysis tools written in Java. It can take raw human language text input and give the base forms of words, their parts of speech, whether they are names of companies, people, etc., normalize and interpret dates, times, and numeric quantities, mark up the structure of sentences in terms of phrases or word dependencies, and indicate which noun phrases refer to the same entities. It was originally developed for English, but now also provides varying levels of support for (Modern Standard) Arabic, (mainland) Chinese, French, German, and Spanish. Stanford CoreNLP is an integrated framework, which make it very easy to apply a bunch of language analysis tools to a piece of text. Starting from plain text, you can run all the tools with just two lines of code. Its analyses provide the foundational building blocks for higher-level and domain-specific text understanding applications. Stanford CoreNLP is a set of stable and well-tested natural language processing tools, widely used by various groups in academia, industry, and government. The tools variously use rule-based, probabilistic machine learning, and deep learning components."
  val text = "I was so happy before she died."
//  val text = "Harry Potter took out his wand, murmured, ‘Lumos!’ and a tiny light appeared at the end of it, just enough to let them watch the path for signs of spiders."
//  val text = "The Battle of Hogwarts was won by good."
  val document = new Annotation(text)
  pipeline.annotate(document)

  document.get(classOf[SentencesAnnotation]).asScala map {
    sentence =>
      val sent: String = sentence.get(classOf[TokensAnnotation]).asScala map {
        token =>
          (
            token.get(classOf[TextAnnotation]),
            token.get(classOf[PartOfSpeechAnnotation]),
            token.get(classOf[LemmaAnnotation]),
            token.get(classOf[NamedEntityTagAnnotation])
          )
      } mkString ", "
      println(sent)
      val tree = sentence.get(classOf[SentimentAnnotatedTree])
      println(RNNCoreAnnotations.getPredictedClass(tree))
//      println(sentence.get(classOf[TreeAnnotation]))
//      println(sentence.get(classOf[CollapsedCCProcessedDependenciesAnnotation]))
  }
}
