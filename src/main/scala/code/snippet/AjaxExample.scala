package code.snippet

import net.liftweb.util.Helpers._
import net.liftweb.http.SHtml
import net.liftweb.http.js._
import JsCmds._
import scala.xml.Text
import net.liftweb.common.{Loggable, Full, Box, Logger}

import net.liftweb.json._
import code.lib.ElasticSearch
import com.twitter.util.{Future,Promise}


case class SearchPosts( name: String, description: String )

class AjaxExample extends Loggable {

  var inputVal = "dos"

  def process(): JsCmd = {

    println("\nfor test: \n\n" +  search(inputVal) + "\n\n")

    println("Received: "+inputVal)
    SetHtml("result", Text(inputVal))
  }

  def search( searchTerm: String ){

    var returnValue:List[SearchPosts] = Nil
    val json_in= parse(""" { "query" : { "term" : { "name": """" + searchTerm + """" }}} """)
    val httpResponse = ElasticSearch.mongoindexSearch( json_in )

    httpResponse.onSuccess{
      response =>
        val json = parse( response.getContent.toString("UTF-8") )
        //println("\njson: \n\n" +  json + "\n\n")
        for {
          JObject(child) <- json
          JField("name", JString(name)) <- child
          JField("description", JString(description)) <- child
        } yield {
          returnValue = List(SearchPosts( name , description ))
          println("\ninside search: \n\n" +  returnValue + "\n\n")
        }
        Future.Done
    }.onFailure{err =>
      logger.error(err)
      Future.Done
    }
    returnValue

  }

  def render = {
    "name=info" #> (
      SHtml.text(inputVal, inputVal = _) ++
        SHtml.hidden(process) )
  }

}