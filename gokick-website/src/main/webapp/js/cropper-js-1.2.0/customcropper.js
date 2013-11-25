function onEndCrop( coords, dimensions )
{
  $( 'x1' ).value = coords.x1;
  $( 'y1' ).value = coords.y1;
  $( 'x2' ).value = coords.x2;
  $( 'y2' ).value = coords.y2;
  $( 'width' ).value = dimensions.width;
  $( 'height' ).value = dimensions.height;
}

Event.observe(
  window,
  'load',
  function()
  {
    new Cropper.ImgWithPreview(
      'idUploadedImage',
      {
        minWidth: 165,
        minHeight: 178,
        ratioDim: { x: 165, y: 178 },
        displayOnInit: true,
        onEndCrop: onEndCrop,
        previewWrap: 'idPreviewPictureCardArea'
      }
    )
  }
);