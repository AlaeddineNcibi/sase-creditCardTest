/*
 * Copyright (c) 2011, Regents of the University of Massachusetts Amherst 
 * All rights reserved.

 * Redistribution and use in source and binary forms, with or without modification, are permitted 
 * provided that the following conditions are met:

 *   * Redistributions of source code must retain the above copyright notice, this list of conditions 
 * 		and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice, this list of conditions 
 * 		and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *   * Neither the name of the University of Massachusetts Amherst nor the names of its contributors 
 * 		may be used to endorse or promote products derived from this software without specific prior written 
 * 		permission.

 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR 
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS 
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE 
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, 
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF 
 * THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package edu.umass.cs.sase.UI;

import java.io.FileWriter;
import java.io.IOException;

import net.sourceforge.jeval.EvaluationException;
import edu.umass.cs.sase.engine.EngineController;
import edu.umass.cs.sase.engine.Profiling;
import edu.umass.cs.sase.stream.StockStreamConfig;
import edu.umass.cs.sase.stream.StreamController;

/**
 * The interface
 * 
 * @author haopeng
 *
 */
public class CommandLineUI {
	/**
	 * The main entry to run the engine under command line
	 * 
	 * @param args
	 *            the inputs 0: the nfa file location 1: the stream config file
	 *            2: print the results or not (1 for print, 0 for not print) 3:
	 *            use sharing techniques or not, ("sharingengine" for use,
	 *            nothing for not use)
	 */
	public static void main(String args[]) throws CloneNotSupportedException,
			EvaluationException, IOException {
		// String nfaFileLocation = "src/main/resources/activity.query";
		String nfaFileLocation = "src/main/resources/creditCard.query";
		// String nfaFileLocation =
		// "src/main/resources/transactionsFarAwayPlaces.query";
		// Delete the content of the outputmatch file
		String FILENAME = "Results/outputmatch.txt";
		FileWriter fw = new FileWriter(FILENAME, false);
		fw.write("Form of the results: \nFM(Final match),RunID, first_event,second_event,third-event \n");
		fw.write("an event: amount:location \n");
		fw.close();

		// Delete the content of the outputpartialmatch file
		String FILENAME2 = "Results/outputpartialmatch.txt";
		FileWriter fw2 = new FileWriter(FILENAME2, false);
		fw2.write("");
		fw2.close();

		// Delete the content of the outputnotmatch file
		String FILENAME3 = "Results/outputnotmatch.txt";
		FileWriter fw3 = new FileWriter(FILENAME3, false);
		fw3.write("");
		fw3.close();

		//
		// String engineType = null;
		// if(args.length > 0){
		// nfaFileLocation = args[0];
		// }
		//
		// if(args.length > 1){
		// streamConfigFile = args[1];
		// }
		//
		// if(args.length > 2){
		// if(Integer.parseInt(args[2])== 1){
		// ConfigFlags.printResults = true;
		// }else{
		// ConfigFlags.printResults = false;
		// }
		// }
		//
		// if(args.length > 3){
		// engineType = args[3];
		// }
		// ParseStockStreamConfig.parseStockEventConfig(streamConfigFile);
		//

		StreamController myStreamController = null;

		EngineController myEngineController = new EngineController();

		// if(engineType != null){
		// myEngineController = new EngineController(engineType);
		// }
		myEngineController.setNfa(nfaFileLocation);

		System.out.println("breack point");
		StockStreamConfig.printConfig();
		for (int i = 0; i < 1; i++) {
			System.out.println("=============>" + i);
			// repreat multiple times for a constant performance
			myEngineController.initializeEngine();
			System.gc();
			System.out.println("\nRepeat No." + (i + 1) + " is started...");
			// /////myStreamController = new
			// StreamController(StockStreamConfig.streamSize,"activityevent");
			myStreamController = new StreamController(
					StockStreamConfig.streamSize, "creditCardevent");
			// generate with probabilty greater then 100, add symbol to
			// eventType
			// myStreamController.generateStockEventsAsTest(5);
			myEngineController.setInput(myStreamController.getMyStream());
			// myStreamController.printStream();
			myEngineController.runEngine();
			System.out.println("\nProfiling results for repeat No." + (i + 1)
					+ " are as follows:");

			Profiling.printProfiling();

		}
	}
}
