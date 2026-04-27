package com.rockthejvm

import java.io.{File, PrintWriter}
import scala.io.Source
import scala.util.{Using, Try, Success, Failure}

//Made by Eeli Remes
// Gemini was used for help with the file operations and file I/O error handling

//This file handles saving/loading data to files. The file that we use is a .txt -file, which works perfectly for this project.
object FileHandler {

  // Default filename to use when saving/loading
  private val defaultFilename = "energy_data.txt"

  /**
   * Saves a list of doubles to a file.
   * Returns Either: Right(()) on success, Left(errorMessage) on failure.
   */
  def saveData(data: List[Double], filename: String = defaultFilename): Either[String, Unit] = {
    // Using automatically closes the PrintWriter once the block is done (Gemini help here)
    Using(new PrintWriter(new File(filename))) { writer =>
      data.foreach(value => writer.println(value.toString))
    } match {
      case Success(_) => Right(())
      case Failure(exception) => Left(s"Error saving to file: ${exception.getMessage}")
      // Gemini help ends here
    }
  }

  /**
   * Loads a list of doubles from a file.
   * Returns Either: Right(List[Double]) on success, Left(errorMessage) on failure.
   */
  def loadData(filename: String = defaultFilename): Either[String, List[Double]] = {
    // Using automatically closes the Source once the block is done (Gemini help here)
    Using(Source.fromFile(filename)) { source =>
      source.getLines()
        .flatMap(line => Try(line.trim.toDouble).toOption) // safely parse Doubles, ignore empty/invalid lines
        .toList
      // Gemini help ends here
    } match {
      case Success(data) => Right(data)
      case Failure(exception) => Left(s"Error loading from file: ${exception.getMessage}")
    }
  }
}
