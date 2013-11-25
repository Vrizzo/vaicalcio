if (!window.DHTMLSuite)var DHTMLSuite = new Object();
var DHTMLSuite_dragDropSimple_curZIndex = 100000;
var DHTMLSuite_dragDropSimple_curObjIndex = false;
DHTMLSuite.dragDropSimple = function (propertyArray) {
    var divElement;
    var dragTimer;
    var cloneNode;
    this.cloneNode = true;
    var callbackOnAfterDrag;
    var callbackOnBeforeDrag;
    var callbackOnDuringDrag;
    var mouse_x;
    var mouse_y;
    var positionSet;
    var dragHandle;
    var allowMoveX;
    var allowMoveY;
    var maxY;
    var minY;
    var minX;
    var maxX;
    var initialXPos;
    var initialYPos;
    this.positionSet = false;
    this.dragHandle = new Array();
    var initOffsetX;
    var initOffsetY;
    this.allowMoveX = true;
    this.allowMoveY = true;
    this.maxY = false;
    this.maxX = false;
    this.minX = false;
    this.minY = false;
    this.callbackOnAfterDrag = false;
    this.callbackOnBeforeDrag = false;
    this.dragStatus = -1;
    try {
        if (!standardObjectsCreated)DHTMLSuite.createStandardObjects()
    } catch (e) {
        alert('Include the dhtmlSuite-common.js file')
    }
    var objectIndex;
    this.objectIndex = DHTMLSuite.variableStorage.arrayDSObjects.length;
    DHTMLSuite.variableStorage.arrayDSObjects[this.objectIndex] = this;
    this.__setInitProps(propertyArray);
    this.__init()
}
DHTMLSuite.dragDropSimple.prototype = {__setInitProps: function (props) {
    if (props.cloneNode === false || props.cloneNode)this.cloneNode = props.cloneNode;
    if (props.allowMoveX === false || props.allowMoveX)this.allowMoveX = props.allowMoveX;
    if (props.allowMoveY === false || props.allowMoveY)this.allowMoveY = props.allowMoveY;
    if (props.minY || props.minY === 0)this.minY = props.minY;
    if (props.maxY || props.maxY === 0)this.maxY = props.maxY;
    if (props.minX || props.minX === 0)this.minX = props.minX;
    if (props.maxX || props.maxX === 0)this.maxX = props.maxX;
    if (!props.initOffsetX)props.initOffsetX = 0;
    if (!props.initOffsetY)props.initOffsetY = 0;
    this.initOffsetX = props.initOffsetX;
    this.initOffsetY = props.initOffsetY;
    if (props.callbackOnBeforeDrag)this.callbackOnBeforeDrag = props.callbackOnBeforeDrag;
    if (props.callbackOnAfterDrag)this.callbackOnAfterDrag = props.callbackOnAfterDrag;
    if (props.callbackOnDuringDrag)this.callbackOnDuringDrag = props.callbackOnDuringDrag;
    props.elementReference = DHTMLSuite.commonObj.getEl(props.elementReference);
    this.divElement = props.elementReference;
    this.initialXPos = DHTMLSuite.commonObj.getLeftPos(this.divElement);
    this.initialYPos = DHTMLSuite.commonObj.getTopPos(this.divElement);
    if (props.dragHandle)this.dragHandle[this.dragHandle.length] = DHTMLSuite.commonObj.getEl(props.dragHandle)
}, __init: function () {
    var ind = this.objectIndex;
    this.divElement.objectIndex = ind;
    this.divElement.setAttribute('objectIndex', ind);
    this.divElement.style.padding = '0px';
    if (this.allowMoveX) {
        this.divElement.style.left = (DHTMLSuite.commonObj.getLeftPos(this.divElement) + this.initOffsetX) + 'px'
    }
    if (this.allowMoveY) {
        this.divElement.style.top = (DHTMLSuite.commonObj.getTopPos(this.divElement) + this.initOffsetY) + 'px'
    }
    this.divElement.style.position = 'absolute';
    this.divElement.style.margin = '0px';
    if (this.divElement.style.zIndex && this.divElement.style.zIndex / 1 > DHTMLSuite_dragDropSimple_curZIndex)DHTMLSuite_dragDropSimple_curZIndex = this.divElement.style.zIndex / 1;
    DHTMLSuite_dragDropSimple_curZIndex = DHTMLSuite_dragDropSimple_curZIndex / 1 + 1;
    this.divElement.style.zIndex = DHTMLSuite_dragDropSimple_curZIndex;
    if (this.cloneNode) {
        var copy = this.divElement.cloneNode(true);
        this.divElement.parentNode.insertBefore(copy, this.divElement);
        copy.style.visibility = 'hidden';
        document.body.appendChild(this.divElement)
    }
    DHTMLSuite.commonObj.addEvent(this.divElement, 'mousedown', function (e) {
        DHTMLSuite.variableStorage.arrayDSObjects[ind].__initDragProcess(e)
    }, ind);
    DHTMLSuite.commonObj.addEvent(document.documentElement, 'mousemove', function (e) {
        DHTMLSuite.variableStorage.arrayDSObjects[ind].__moveDragableElement(e)
    }, ind);
    DHTMLSuite.commonObj.addEvent(document.documentElement, 'mouseup', function (e) {
        DHTMLSuite.variableStorage.arrayDSObjects[ind].__stopDragProcess(e)
    }, ind);
    if (!document.documentElement.onselectstart)document.documentElement.onselectstart = function () {
        return DHTMLSuite.commonObj.__isTextSelOk()
    }
}, setCallbackOnAfterDrag: function (functionName) {
    this.callbackOnAfterDrag = functionName
}, setCallbackOnBeforeDrag: function (functionName) {
    this.callbackOnBeforeDrag = functionName
}, addDragHandle: function (dragHandle) {
    this.dragHandle[this.dragHandle.length] = dragHandle
}, __initDragProcess: function (e) {
    if (document.all)e = event;
    var ind = this.objectIndex;
    DHTMLSuite_dragDropSimple_curObjIndex = ind;
    var thisObject = DHTMLSuite.variableStorage.arrayDSObjects[ind];
    if (!DHTMLSuite.commonObj.isObjectClicked(thisObject.divElement, e))return;
    if (thisObject.divElement.style.zIndex && thisObject.divElement.style.zIndex / 1 > DHTMLSuite_dragDropSimple_curZIndex)DHTMLSuite_dragDropSimple_curZIndex = thisObject.divElement.style.zIndex / 1;
    DHTMLSuite_dragDropSimple_curZIndex = DHTMLSuite_dragDropSimple_curZIndex / 1 + 1;
    thisObject.divElement.style.zIndex = DHTMLSuite_dragDropSimple_curZIndex;
    if (thisObject.callbackOnBeforeDrag) {
        thisObject.__handleCallback('beforeDrag', e)
    }
    if (thisObject.dragHandle.length > 0) {
        var objectFound;
        for (var no = 0; no < thisObject.dragHandle.length; no++) {
            if (!objectFound)objectFound = DHTMLSuite.commonObj.isObjectClicked(thisObject.dragHandle[no], e)
        }
        if (!objectFound)return
    }
    DHTMLSuite.commonObj.__setTextSelOk(false);
    thisObject.mouse_x = e.clientX;
    thisObject.mouse_y = e.clientY;
    thisObject.el_x = thisObject.divElement.style.left.replace('px', '') / 1;
    thisObject.el_y = thisObject.divElement.style.top.replace('px', '') / 1;
    thisObject.dragTimer = 0;
    thisObject.__waitBeforeDragProcessStarts();
    return false
}, __waitBeforeDragProcessStarts: function () {
    var ind = this.objectIndex;
    if (this.dragTimer >= 0 && this.dragTimer < 5) {
        this.dragTimer++;
        setTimeout('DHTMLSuite.variableStorage.arrayDSObjects[' + ind + '].__waitBeforeDragProcessStarts()', 5)
    }
}, __moveDragableElement: function (e) {
    if (document.all)e = event;
    var ind = this.objectIndex;
    var thisObj = DHTMLSuite.variableStorage.arrayDSObjects[ind];
    if (DHTMLSuite.clientInfoObj.isMSIE && e.button != 1)return thisObj.__stopDragProcess();
    if (thisObj.dragTimer == 5) {
        if (thisObj.allowMoveX) {
            var leftPos = (e.clientX - this.mouse_x + this.el_x);
            if (this.maxX !== false) {
                if (leftPos + document.documentElement.scrollLeft > this.initialXPos + this.maxX) {
                    leftPos = this.initialXPos + this.maxX
                }
            }
            if (this.minX !== false) {
                if (leftPos + document.documentElement.scrollLeft < this.initialXPos + this.minX) {
                    leftPos = this.initialXPos + this.minX
                }
            }
            thisObj.divElement.style.left = leftPos + 'px'
        }
        if (thisObj.allowMoveY) {
            var topPos = (e.clientY - thisObj.mouse_y + thisObj.el_y);
            if (this.maxY !== false) {
                if (topPos > this.initialYPos + this.maxY) {
                    topPos = this.initialYPos + this.maxY
                }
            }
            if (this.minY !== false) {
                if (topPos < this.initialYPos + this.minY) {
                    topPos = this.initialYPos + this.minY
                }
            }
            thisObj.divElement.style.top = topPos + 'px'
        }
        if (this.callbackOnDuringDrag)this.__handleCallback('duringDrag', e)
    }
    return false
}, __stopDragProcess: function (e) {
    var ind = this.objectIndex;
    DHTMLSuite.commonObj.__setTextSelOk(true);
    var thisObj = DHTMLSuite.variableStorage.arrayDSObjects[ind];
    if (thisObj.dragTimer == 5) {
        thisObj.__handleCallback('afterDrag', e)
    }
    thisObj.dragTimer = -1
}, __handleCallback: function (action, e) {
    var callbackString = '';
    switch (action) {
        case "afterDrag":
            callbackString = this.callbackOnAfterDrag;
            break;
        case "beforeDrag":
            callbackString = this.callbackOnBeforeDrag;
            break;
        case "duringDrag":
            callbackString = this.callbackOnDuringDrag;
            break
    }
    if (callbackString) {
        callbackString = callbackString + '(e)';
        try {
            eval(callbackString)
        } catch (e) {
            alert('Could not execute callback function(' + callbackString + ')after drag')
        }
    }
}, __setNewCurrentZIndex: function (zIndex) {
    if (zIndex > DHTMLSuite_dragDropSimple_curZIndex) {
        DHTMLSuite_dragDropSimple_curZIndex = zIndex / 1 + 1
    }
}}