package com.zoho.officeintegrator.v1.examples.writer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.zoho.api.authenticator.Auth;
import com.zoho.api.authenticator.Token;
import com.zoho.officeintegrator.Initializer;
import com.zoho.officeintegrator.dc.USDataCenter;
import com.zoho.officeintegrator.logger.Logger;
import com.zoho.officeintegrator.logger.Logger.Levels;
import com.zoho.officeintegrator.util.APIResponse;
import com.zoho.officeintegrator.util.StreamWrapper;
import com.zoho.officeintegrator.v1.Authentication;
import com.zoho.officeintegrator.v1.InvalidConfigurationException;
import com.zoho.officeintegrator.v1.PreviewParameters;
import com.zoho.officeintegrator.v1.PreviewResponse;
import com.zoho.officeintegrator.v1.V1Operations;
import com.zoho.officeintegrator.v1.WriterResponseHandler;

public class PreviewDocument {

	private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(PreviewDocument.class.getName());

	public static void main(String args[]) {
		
		try {
			//SDK Initialisation code starts. Move this code to common place and initialise once

			initializeSdk();

			V1Operations sdkOperations = new V1Operations();
			PreviewParameters previewParams = new PreviewParameters();
			
//			previewParams.setUrl("https://demo.office-integrator.com/zdocs/Graphic-Design-Proposal.docx");
			
			String inputFilePath = "/Users/praba-2086/Downloads/894b7e4f-070b-4801-8b7c-18ec1d7d1c12.docx";
			StreamWrapper documentStreamWrapper = new StreamWrapper(inputFilePath);
			
			previewParams.setDocument(documentStreamWrapper);
			
			APIResponse<WriterResponseHandler> response = sdkOperations.createDocumentPreview(previewParams);
			int responseStatusCode = response.getStatusCode();
			
			if ( responseStatusCode >= 200 && responseStatusCode <= 299 ) {
				PreviewResponse previewResponse = (PreviewResponse) response.getObject();

				String previewUrl = previewResponse.getPreviewUrl();
				
				LOGGER.log(Level.INFO, "Document preview url - {0}", new Object[] { previewUrl }); //No I18N
			} else {
				InvalidConfigurationException invalidConfiguration = (InvalidConfigurationException) response.getObject();

				String errorMessage = invalidConfiguration.getMessage();
				
				/*Long errorCode = invalidConfiguration.getCode();
				String errorKeyName = invalidConfiguration.getKeyName();
				String errorParameterName = invalidConfiguration.getParameterName();*/
				
				LOGGER.log(Level.INFO, "Document configuration error - {0}", new Object[] { errorMessage }); //No I18N
			}
			
		} catch (Exception e) {
			LOGGER.log(Level.INFO, "Exception in creating preview document session url - ", e); //No I18N
		}
	}
	
	public static boolean initializeSdk() {
		boolean status = false;

		try {
			Logger logger = new Logger.Builder()
			        .level(Levels.INFO)
			        //.filePath("<file absolute path where logs would be written>") //No I18N
			        .build();

			List<Token> tokens = new ArrayList<Token>();
			Auth auth = new Auth.Builder().addParam("apikey", "2ae438cf864488657cc9754a27daa480").authenticationSchema(new Authentication.TokenFlow()).build();
			
			tokens.add(auth);
			
			new Initializer.Builder()
				.environment(new USDataCenter.Production())
				.tokens(tokens)
				.logger(logger)
				.initialize();
			
			status = true;
} catch (Exception e) {
			LOGGER.log(Level.INFO, "Exception in creating document session url - ", e); //No I18N
		}
		return status;
	}
}