

OpenLayers.Map.prototype.setRealCenter =  function(lonlat, zoom, dragging, forceZoomChange) {
		if(this.resolutions){
			var _resolution = this.pyramid.getResolutionForLevel(zoom);
			var isbig = false;
			for(var i=0;i<this.resolutions.length;i++){
				if(_resolution*1e5==this.resolutions[i]*1e5){
					zoom = i;
					isbig = true;
					break;
				}else if(_resolution*1e5<this.resolutions[i]*1e5){
					isbig = false;
				}else if(_resolution*1e5>this.resolutions[i]*1e5){
					if(isbig == false){
						if(i==0){
							zoom = i;
						}else{
							var prezoom = this.pyramid.getLevelForResolution(this.resolutions[i-1]);
							var nextzoom = this.pyramid.getLevelForResolution(this.resolutions[i]);
							if((nextzoom-zoom)<(zoom-prezoom)){
								zoom=i;
							}else{
								zoom=i-1;
							}
						}
						isbig = true;
						
						break;
					}
					
				}
			}
			if(isbig == false){zoom=i-1}
        
		}else{
			zoom = zoom - this.pyramid.getLevelForResolution(this.getLayersMaxResolution());
		}
        this.panTween && this.panTween.stop();             
        this.moveTo(lonlat, zoom, {
            'dragging': dragging,
            'forceZoomChange': forceZoomChange
        });
    };
	OpenLayers.Map.prototype.getRealZoom =  function() {
	        if(this.resolutions){
	        	return this.pyramid.getLevelForResolution(this.resolutions[this.zoom]);
	        }else{
	        	return this.zoom + this.pyramid.getLevelForResolution(this.getLayersMaxResolution());
	        }
	    };
	
	/**
	 * 解决openlayer在无瓦片情况下显示红叉叉
	 */
	 OpenLayers.Tile.Image.prototype.onImageLoad = function() {
		  var img = this.imgDiv;
		  this.stopLoading();
		  var imgClassName = img.className;
		  if(imgClassName && imgClassName.indexOf("olImageLoadError") != -1) {
		  	img.style.visibility = "hidden";
		  } else {
		  	img.style.visibility = "inherit";
		  }
		  img.style.opacity = this.layer.opacity;
		  this.isLoading = false;
		  this.canvasContext = null;
		  this.events.triggerEvent("loadend");
		  if(this.layerAlphaHack === true) {
		    img.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + img.src + "', sizingMethod='scale')"
		  }
	}; 

	Geo.View2D.Map.prototype.unloadLayerGroup = function(){
		var layerGroup = this.layerGroup;
        if (!layerGroup) {
			return false;
		}
		layerGroup.removeMap();
        this.layerGroup = null;
		this.resolutions = null;
    };