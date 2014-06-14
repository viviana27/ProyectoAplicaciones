package com.SubirDescargarArchivos;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;
import com.SubirDescargarArchivos.Util;

public class SubirDescargarArchivos extends GenericForwardComposer<Component> {
	private Media media;

	@NotifyChange("media")
	@Command
	public void uploadFile(
			@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent event) {
		
		media = event.getMedia();
		
		if (Util.uploadFile(media))
		alert("Archivo Cargado....!");
		//	Messagebox.show(Labels.getLabel("app.successfull"));
		else
			Messagebox.show(Labels.getLabel("app.error"));
	}
	
	public Media getMedia() {
		return media;
	}
	//simply download the file
		@Command
		public void downloadFile(){
			if(media != null)
				Filedownload.save(media);

		}
}
