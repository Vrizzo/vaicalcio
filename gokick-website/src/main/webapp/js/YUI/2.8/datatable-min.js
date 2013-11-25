/*
Copyright (c) 2009, Yahoo! Inc. All rights reserved.
Code licensed under the BSD License:
http://developer.yahoo.net/yui/license.txt
version: 2.8.0r4
*/
YAHOO.util.Chain=function(){
  this.q=[].slice.call(arguments);
  this.createEvent("end");
};

YAHOO.util.Chain.prototype={
  id:0,
  run:function(){
    var F=this.q[0],C;
    if(!F){
      this.fireEvent("end");
      return this;
    }else{
      if(this.id){
        return this;
      }
    }
    C=F.method||F;
  if(typeof C==="function"){
    var E=F.scope||{},B=F.argument||[],A=F.timeout||0,D=this;
    if(!(B instanceof Array)){
      B=[B];
    }
    if(A<0){
      this.id=A;
      if(F.until){
        for(;!F.until();){
          C.apply(E,B);
        }
        }else{
      if(F.iterations){
        for(;F.iterations-->0;){
          C.apply(E,B);
        }
        }else{
      C.apply(E,B);
    }
  }
  this.q.shift();
  this.id=0;
  return this.run();
}else{
  if(F.until){
    if(F.until()){
      this.q.shift();
      return this.run();
    }
  }else{
  if(!F.iterations||!--F.iterations){
    this.q.shift();
  }
}
this.id=setTimeout(function(){
  C.apply(E,B);
  if(D.id){
    D.id=0;
    D.run();
  }
},A);
}
}
return this;
},
add:function(A){
  this.q.push(A);
  return this;
},
pause:function(){
  if(this.id>0){
    clearTimeout(this.id);
  }
  this.id=0;
  return this;
},
stop:function(){
  this.pause();
  this.q=[];
  return this;
}
};

YAHOO.lang.augmentProto(YAHOO.util.Chain,YAHOO.util.EventProvider);
YAHOO.widget.ColumnSet=function(A){
  this._sId="yui-cs"+YAHOO.widget.ColumnSet._nCount;
  A=YAHOO.widget.DataTable._cloneObject(A);
  this._init(A);
  YAHOO.widget.ColumnSet._nCount++;
};

YAHOO.widget.ColumnSet._nCount=0;
YAHOO.widget.ColumnSet.prototype={
  _sId:null,
  _aDefinitions:null,
  tree:null,
  flat:null,
  keys:null,
  headers:null,
  _init:function(I){
    var J=[];
    var A=[];
    var G=[];
    var E=[];
    var C=-1;
    var B=function(M,S){
      C++;
      if(!J[C]){
        J[C]=[];
      }
      for(var O=0;O<M.length;O++){
        var K=M[O];
        var Q=new YAHOO.widget.Column(K);
        K.yuiColumnId=Q._sId;
        A.push(Q);
        if(S){
          Q._oParent=S;
        }
        if(YAHOO.lang.isArray(K.children)){
          Q.children=K.children;
          var R=0;
          var P=function(V){
            var W=V.children;
            for(var U=0;U<W.length;U++){
              if(YAHOO.lang.isArray(W[U].children)){
                P(W[U]);
              }else{
                R++;
              }
            }
            };

      P(K);
        Q._nColspan=R;
        var T=K.children;
        for(var N=0;N<T.length;N++){
        var L=T[N];
        if(Q.className&&(L.className===undefined)){
          L.className=Q.className;
        }
        if(Q.editor&&(L.editor===undefined)){
          L.editor=Q.editor;
        }
        if(Q.editorOptions&&(L.editorOptions===undefined)){
          L.editorOptions=Q.editorOptions;
        }
        if(Q.formatter&&(L.formatter===undefined)){
          L.formatter=Q.formatter;
        }
        if(Q.resizeable&&(L.resizeable===undefined)){
          L.resizeable=Q.resizeable;
        }
        if(Q.sortable&&(L.sortable===undefined)){
          L.sortable=Q.sortable;
        }
        if(Q.hidden){
          L.hidden=true;
        }
        if(Q.width&&(L.width===undefined)){
          L.width=Q.width;
        }
        if(Q.minWidth&&(L.minWidth===undefined)){
          L.minWidth=Q.minWidth;
        }
        if(Q.maxAutoWidth&&(L.maxAutoWidth===undefined)){
          L.maxAutoWidth=Q.maxAutoWidth;
        }
        if(Q.type&&(L.type===undefined)){
          L.type=Q.type;
        }
        if(Q.type&&!Q.formatter){
          Q.formatter=Q.type;
        }
        if(Q.text&&!YAHOO.lang.isValue(Q.label)){
          Q.label=Q.text;
        }
        if(Q.parser){}
        if(Q.sortOptions&&((Q.sortOptions.ascFunction)||(Q.sortOptions.descFunction))){}
      }
      if(!J[C+1]){
        J[C+1]=[];
      }
      B(T,Q);
    }else{
      Q._nKeyIndex=G.length;
      Q._nColspan=1;
      G.push(Q);
    }
    J[C].push(Q);
  }
  C--;
};

if(YAHOO.lang.isArray(I)){
  B(I);
  this._aDefinitions=I;
}else{
  return null;
}
var F;
var D=function(L){
  var M=1;
  var O;
  var N;
  var P=function(T,S){
    S=S||1;
    for(var U=0;U<T.length;U++){
      var R=T[U];
      if(YAHOO.lang.isArray(R.children)){
        S++;
        P(R.children,S);
        S--;
      }else{
        if(S>M){
          M=S;
        }
      }
    }
  };

for(var K=0;K<L.length;K++){
  O=L[K];
  P(O);
  for(var Q=0;Q<O.length;Q++){
    N=O[Q];
    if(!YAHOO.lang.isArray(N.children)){
      N._nRowspan=M;
    }else{
      N._nRowspan=1;
    }
  }
  M=1;
}
};

D(J);
for(F=0;F<J[0].length;F++){
  J[0][F]._nTreeIndex=F;
}
var H=function(K,L){
  E[K].push(L.getSanitizedKey());
  if(L._oParent){
    H(K,L._oParent);
  }
};

for(F=0;F<G.length;F++){
  E[F]=[];
  H(F,G[F]);
  E[F]=E[F].reverse();
}
this.tree=J;
this.flat=A;
this.keys=G;
this.headers=E;
},
getId:function(){
  return this._sId;
},
toString:function(){
  return"ColumnSet instance "+this._sId;
},
getDefinitions:function(){
  var A=this._aDefinitions;
  var B=function(E,G){
    for(var D=0;D<E.length;D++){
      var F=E[D];
      var I=G.getColumnById(F.yuiColumnId);
      if(I){
        var H=I.getDefinition();
        for(var C in H){
          if(YAHOO.lang.hasOwnProperty(H,C)){
            F[C]=H[C];
          }
        }
        }
        if(YAHOO.lang.isArray(F.children)){
      B(F.children,G);
    }
  }
};

B(A,this);
this._aDefinitions=A;
return A;
},
getColumnById:function(C){
  if(YAHOO.lang.isString(C)){
    var A=this.flat;
    for(var B=A.length-1;B>-1;B--){
      if(A[B]._sId===C){
        return A[B];
      }
    }
    }
  return null;
},
getColumn:function(C){
  if(YAHOO.lang.isNumber(C)&&this.keys[C]){
    return this.keys[C];
  }else{
    if(YAHOO.lang.isString(C)){
      var A=this.flat;
      var D=[];
      for(var B=0;B<A.length;B++){
        if(A[B].key===C){
          D.push(A[B]);
        }
      }
      if(D.length===1){
      return D[0];
    }else{
      if(D.length>1){
        return D;
      }
    }
  }
}
return null;
},
getDescendants:function(D){
  var B=this;
  var C=[];
  var A;
  var E=function(F){
    C.push(F);
    if(F.children){
      for(A=0;A<F.children.length;A++){
        E(B.getColumn(F.children[A].key));
      }
      }
    };

E(D);
return C;
}
};

YAHOO.widget.Column=function(B){
  this._sId="yui-col"+YAHOO.widget.Column._nCount;
  if(B&&YAHOO.lang.isObject(B)){
    for(var A in B){
      if(A){
        this[A]=B[A];
      }
    }
    }
    if(!YAHOO.lang.isValue(this.key)){
  this.key="yui-dt-col"+YAHOO.widget.Column._nCount;
}
if(!YAHOO.lang.isValue(this.field)){
  this.field=this.key;
}
YAHOO.widget.Column._nCount++;
if(this.width&&!YAHOO.lang.isNumber(this.width)){
  this.width=null;
}
if(this.editor&&YAHOO.lang.isString(this.editor)){
  this.editor=new YAHOO.widget.CellEditor(this.editor,this.editorOptions);
}
};

YAHOO.lang.augmentObject(YAHOO.widget.Column,{
  _nCount:0,
  formatCheckbox:function(B,A,C,D){
    YAHOO.widget.DataTable.formatCheckbox(B,A,C,D);
  },
  formatCurrency:function(B,A,C,D){
    YAHOO.widget.DataTable.formatCurrency(B,A,C,D);
  },
  formatDate:function(B,A,C,D){
    YAHOO.widget.DataTable.formatDate(B,A,C,D);
  },
  formatEmail:function(B,A,C,D){
    YAHOO.widget.DataTable.formatEmail(B,A,C,D);
  },
  formatLink:function(B,A,C,D){
    YAHOO.widget.DataTable.formatLink(B,A,C,D);
  },
  formatNumber:function(B,A,C,D){
    YAHOO.widget.DataTable.formatNumber(B,A,C,D);
  },
  formatSelect:function(B,A,C,D){
    YAHOO.widget.DataTable.formatDropdown(B,A,C,D);
  }
});
YAHOO.widget.Column.prototype={
  _sId:null,
  _nKeyIndex:null,
  _nTreeIndex:null,
  _nColspan:1,
  _nRowspan:1,
  _oParent:null,
  _elTh:null,
  _elThLiner:null,
  _elThLabel:null,
  _elResizer:null,
  _nWidth:null,
  _dd:null,
  _ddResizer:null,
  key:null,
  field:null,
  label:null,
  abbr:null,
  children:null,
  width:null,
  minWidth:null,
  maxAutoWidth:null,
  hidden:false,
  selected:false,
  className:null,
  formatter:null,
  currencyOptions:null,
  dateOptions:null,
  dropdownOptions:null,
  editor:null,
  resizeable:false,
  sortable:false,
  sortOptions:null,
  getId:function(){
    return this._sId;
  },
  toString:function(){
    return"Column instance "+this._sId;
  },
  getDefinition:function(){
    var A={};

    A.abbr=this.abbr;
    A.className=this.className;
    A.editor=this.editor;
    A.editorOptions=this.editorOptions;
    A.field=this.field;
    A.formatter=this.formatter;
    A.hidden=this.hidden;
    A.key=this.key;
    A.label=this.label;
    A.minWidth=this.minWidth;
    A.maxAutoWidth=this.maxAutoWidth;
    A.resizeable=this.resizeable;
    A.selected=this.selected;
    A.sortable=this.sortable;
    A.sortOptions=this.sortOptions;
    A.width=this.width;
    return A;
  },
  getKey:function(){
    return this.key;
  },
  getField:function(){
    return this.field;
  },
  getSanitizedKey:function(){
    return this.getKey().replace(/[^\w\-]/g,"");
  },
  getKeyIndex:function(){
    return this._nKeyIndex;
  },
  getTreeIndex:function(){
    return this._nTreeIndex;
  },
  getParent:function(){
    return this._oParent;
  },
  getColspan:function(){
    return this._nColspan;
  },
  getColSpan:function(){
    return this.getColspan();
  },
  getRowspan:function(){
    return this._nRowspan;
  },
  getThEl:function(){
    return this._elTh;
  },
  getThLinerEl:function(){
    return this._elThLiner;
  },
  getResizerEl:function(){
    return this._elResizer;
  },
  getColEl:function(){
    return this.getThEl();
  },
  getIndex:function(){
    return this.getKeyIndex();
  },
  format:function(){}
};

YAHOO.util.Sort={
  compare:function(B,A,C){
    if((B===null)||(typeof B=="undefined")){
      if((A===null)||(typeof A=="undefined")){
        return 0;
      }else{
        return 1;
      }
    }else{
    if((A===null)||(typeof A=="undefined")){
      return -1;
    }
  }
  if(B.constructor==String){
  B=B.toLowerCase();
}
if(A.constructor==String){
  A=A.toLowerCase();
}
if(B<A){
  return(C)?1:-1;
}else{
  if(B>A){
    return(C)?-1:1;
  }else{
    return 0;
  }
}
}
};

YAHOO.widget.ColumnDD=function(D,A,C,B){
  if(D&&A&&C&&B){
    this.datatable=D;
    this.table=D.getTableEl();
    this.column=A;
    this.headCell=C;
    this.pointer=B;
    this.newIndex=null;
    this.init(C);
    this.initFrame();
    this.invalidHandleTypes={};

    this.setPadding(10,0,(this.datatable.getTheadEl().offsetHeight+10),0);
    YAHOO.util.Event.on(window,"resize",function(){
      this.initConstraints();
    },this,true);
  }else{}
};

if(YAHOO.util.DDProxy){
  YAHOO.extend(YAHOO.widget.ColumnDD,YAHOO.util.DDProxy,{
    initConstraints:function(){
      var G=YAHOO.util.Dom.getRegion(this.table),D=this.getEl(),F=YAHOO.util.Dom.getXY(D),C=parseInt(YAHOO.util.Dom.getStyle(D,"width"),10),A=parseInt(YAHOO.util.Dom.getStyle(D,"height"),10),E=((F[0]-G.left)+15),B=((G.right-F[0]-C)+15);
      this.setXConstraint(E,B);
      this.setYConstraint(10,10);
    },
    _resizeProxy:function(){
      YAHOO.widget.ColumnDD.superclass._resizeProxy.apply(this,arguments);
      var A=this.getDragEl(),B=this.getEl();
      YAHOO.util.Dom.setStyle(this.pointer,"height",(this.table.parentNode.offsetHeight+10)+"px");
      YAHOO.util.Dom.setStyle(this.pointer,"display","block");
      var C=YAHOO.util.Dom.getXY(B);
      YAHOO.util.Dom.setXY(this.pointer,[C[0],(C[1]-5)]);
      YAHOO.util.Dom.setStyle(A,"height",this.datatable.getContainerEl().offsetHeight+"px");
      YAHOO.util.Dom.setStyle(A,"width",(parseInt(YAHOO.util.Dom.getStyle(A,"width"),10)+4)+"px");
      YAHOO.util.Dom.setXY(this.dragEl,C);
    },
    onMouseDown:function(){
      this.initConstraints();
      this.resetConstraints();
    },
    clickValidator:function(B){
      if(!this.column.hidden){
        var A=YAHOO.util.Event.getTarget(B);
        return(this.isValidHandleChild(A)&&(this.id==this.handleElId||this.DDM.handleWasClicked(A,this.id)));
      }
    },
  onDragOver:function(H,A){
    var F=this.datatable.getColumn(A);
    if(F){
      var C=F.getTreeIndex();
      while((C===null)&&F.getParent()){
        F=F.getParent();
        C=F.getTreeIndex();
      }
      if(C!==null){
        var B=F.getThEl();
        var K=C;
        var D=YAHOO.util.Event.getPageX(H),I=YAHOO.util.Dom.getX(B),J=I+((YAHOO.util.Dom.get(B).offsetWidth)/2),E=this.column.getTreeIndex();
        if(D<J){
          YAHOO.util.Dom.setX(this.pointer,I);
        }else{
          var G=parseInt(B.offsetWidth,10);
          YAHOO.util.Dom.setX(this.pointer,(I+G));
          K++;
        }
        if(C>E){
          K--;
        }
        if(K<0){
          K=0;
        }else{
          if(K>this.datatable.getColumnSet().tree[0].length){
            K=this.datatable.getColumnSet().tree[0].length;
          }
        }
        this.newIndex=K;
    }
  }
  },
onDragDrop:function(){
  this.datatable.reorderColumn(this.column,this.newIndex);
},
endDrag:function(){
  this.newIndex=null;
  YAHOO.util.Dom.setStyle(this.pointer,"display","none");
}
});
}
YAHOO.util.ColumnResizer=function(E,C,D,A,B){
  if(E&&C&&D&&A){
    this.datatable=E;
    this.column=C;
    this.headCell=D;
    this.headCellLiner=C.getThLinerEl();
    this.resizerLiner=D.firstChild;
    this.init(A,A,{
      dragOnly:true,
      dragElId:B.id
      });
    this.initFrame();
    this.resetResizerEl();
    this.setPadding(0,1,0,0);
  }else{}
};

if(YAHOO.util.DD){
  YAHOO.extend(YAHOO.util.ColumnResizer,YAHOO.util.DDProxy,{
    resetResizerEl:function(){
      var A=YAHOO.util.Dom.get(this.handleElId).style;
      A.left="auto";
      A.right=0;
      A.top="auto";
      A.bottom=0;
      A.height=this.headCell.offsetHeight+"px";
    },
    onMouseUp:function(G){
      var E=this.datatable.getColumnSet().keys,B;
      for(var C=0,A=E.length;C<A;C++){
        B=E[C];
        if(B._ddResizer){
          B._ddResizer.resetResizerEl();
        }
      }
      this.resetResizerEl();
    var D=this.headCellLiner;
    var F=D.offsetWidth-(parseInt(YAHOO.util.Dom.getStyle(D,"paddingLeft"),10)|0)-(parseInt(YAHOO.util.Dom.getStyle(D,"paddingRight"),10)|0);
    this.datatable.fireEvent("columnResizeEvent",{
      column:this.column,
      target:this.headCell,
      width:F
    });
  },
  onMouseDown:function(A){
    this.startWidth=this.headCellLiner.offsetWidth;
    this.startX=YAHOO.util.Event.getXY(A)[0];
    this.nLinerPadding=(parseInt(YAHOO.util.Dom.getStyle(this.headCellLiner,"paddingLeft"),10)|0)+(parseInt(YAHOO.util.Dom.getStyle(this.headCellLiner,"paddingRight"),10)|0);
  },
  clickValidator:function(B){
    if(!this.column.hidden){
      var A=YAHOO.util.Event.getTarget(B);
      return(this.isValidHandleChild(A)&&(this.id==this.handleElId||this.DDM.handleWasClicked(A,this.id)));
    }
  },
  startDrag:function(){
    var E=this.datatable.getColumnSet().keys,D=this.column.getKeyIndex(),B;
    for(var C=0,A=E.length;C<A;C++){
      B=E[C];
      if(B._ddResizer){
        YAHOO.util.Dom.get(B._ddResizer.handleElId).style.height="1em";
      }
    }
    },
onDrag:function(C){
  var D=YAHOO.util.Event.getXY(C)[0];
  if(D>YAHOO.util.Dom.getX(this.headCellLiner)){
    var A=D-this.startX;
    var B=this.startWidth+A-this.nLinerPadding;
    if(B>0){
      this.datatable.setColumnWidth(this.column,B);
    }
  }
}
});
}(function(){
  var G=YAHOO.lang,A=YAHOO.util,E=YAHOO.widget,C=A.Dom,F=A.Event,D=E.DataTable;
  YAHOO.widget.RecordSet=function(H){
    this._sId="yui-rs"+E.RecordSet._nCount;
    E.RecordSet._nCount++;
    this._records=[];
    if(H){
      if(G.isArray(H)){
        this.addRecords(H);
      }else{
        if(G.isObject(H)){
          this.addRecord(H);
        }
      }
    }
};

var B=E.RecordSet;
B._nCount=0;
B.prototype={
  _sId:null,
  _addRecord:function(J,H){
    var I=new YAHOO.widget.Record(J);
    if(YAHOO.lang.isNumber(H)&&(H>-1)){
      this._records.splice(H,0,I);
    }else{
      this._records[this._records.length]=I;
    }
    return I;
  },
  _setRecord:function(I,H){
    if(!G.isNumber(H)||H<0){
      H=this._records.length;
    }
    return(this._records[H]=new E.Record(I));
  },
  _deleteRecord:function(I,H){
    if(!G.isNumber(H)||(H<0)){
      H=1;
    }
    this._records.splice(I,H);
  },
  getId:function(){
    return this._sId;
  },
  toString:function(){
    return"RecordSet instance "+this._sId;
  },
  getLength:function(){
    return this._records.length;
  },
  getRecord:function(H){
    var I;
    if(H instanceof E.Record){
      for(I=0;I<this._records.length;I++){
        if(this._records[I]&&(this._records[I]._sId===H._sId)){
          return H;
        }
      }
      }else{
  if(G.isNumber(H)){
    if((H>-1)&&(H<this.getLength())){
      return this._records[H];
    }
  }else{
  if(G.isString(H)){
    for(I=0;I<this._records.length;I++){
      if(this._records[I]&&(this._records[I]._sId===H)){
        return this._records[I];
      }
    }
    }
}
}
return null;
},
getRecords:function(I,H){
  if(!G.isNumber(I)){
    return this._records;
  }
  if(!G.isNumber(H)){
    return this._records.slice(I);
  }
  return this._records.slice(I,I+H);
},
hasRecords:function(I,H){
  var K=this.getRecords(I,H);
  for(var J=0;J<H;++J){
    if(typeof K[J]==="undefined"){
      return false;
    }
  }
  return true;
},
getRecordIndex:function(I){
  if(I){
    for(var H=this._records.length-1;H>-1;H--){
      if(this._records[H]&&I.getId()===this._records[H].getId()){
        return H;
      }
    }
    }
  return null;
},
addRecord:function(J,H){
  if(G.isObject(J)){
    var I=this._addRecord(J,H);
    this.fireEvent("recordAddEvent",{
      record:I,
      data:J
    });
    return I;
  }else{
    return null;
  }
},
addRecords:function(L,K){
  if(G.isArray(L)){
    var O=[],I,M,H;
    K=G.isNumber(K)?K:this._records.length;
    I=K;
    for(M=0,H=L.length;M<H;++M){
      if(G.isObject(L[M])){
        var J=this._addRecord(L[M],I++);
        O.push(J);
      }
    }
    this.fireEvent("recordsAddEvent",{
    records:O,
    data:L
  });
  return O;
}else{
  if(G.isObject(L)){
    var N=this._addRecord(L);
    this.fireEvent("recordsAddEvent",{
      records:[N],
      data:L
    });
    return N;
  }else{
    return null;
  }
}
},
setRecord:function(J,H){
  if(G.isObject(J)){
    var I=this._setRecord(J,H);
    this.fireEvent("recordSetEvent",{
      record:I,
      data:J
    });
    return I;
  }else{
    return null;
  }
},
setRecords:function(L,K){
  var O=E.Record,I=G.isArray(L)?L:[L],N=[],M=0,H=I.length,J=0;
  K=parseInt(K,10)|0;
  for(;M<H;++M){
    if(typeof I[M]==="object"&&I[M]){
      N[J++]=this._records[K+M]=new O(I[M]);
    }
  }
  this.fireEvent("recordsSetEvent",{
  records:N,
  data:L
});
this.fireEvent("recordsSet",{
  records:N,
  data:L
});
if(I.length&&!N.length){}
return N.length>1?N:N[0];
},
updateRecord:function(H,L){
  var J=this.getRecord(H);
  if(J&&G.isObject(L)){
    var K={};

    for(var I in J._oData){
      if(G.hasOwnProperty(J._oData,I)){
        K[I]=J._oData[I];
      }
    }
    J._oData=L;
  this.fireEvent("recordUpdateEvent",{
    record:J,
    newData:L,
    oldData:K
  });
  return J;
}else{
  return null;
}
},
updateKey:function(H,I,J){
  this.updateRecordValue(H,I,J);
},
updateRecordValue:function(H,K,N){
  var J=this.getRecord(H);
  if(J){
    var M=null;
    var L=J._oData[K];
    if(L&&G.isObject(L)){
      M={};

      for(var I in L){
        if(G.hasOwnProperty(L,I)){
          M[I]=L[I];
        }
      }
      }else{
  M=L;
}
J._oData[K]=N;
this.fireEvent("keyUpdateEvent",{
  record:J,
  key:K,
  newData:N,
  oldData:M
});
this.fireEvent("recordValueUpdateEvent",{
  record:J,
  key:K,
  newData:N,
  oldData:M
});
}else{}
},
replaceRecords:function(H){
  this.reset();
  return this.addRecords(H);
},
sortRecords:function(H,J,I){
  return this._records.sort(function(L,K){
    return H(L,K,J,I);
  });
},
reverseRecords:function(){
  return this._records.reverse();
},
deleteRecord:function(H){
  if(G.isNumber(H)&&(H>-1)&&(H<this.getLength())){
    var I=E.DataTable._cloneObject(this.getRecord(H).getData());
    this._deleteRecord(H);
    this.fireEvent("recordDeleteEvent",{
      data:I,
      index:H
    });
    return I;
  }else{
    return null;
  }
},
deleteRecords:function(J,H){
  if(!G.isNumber(H)){
    H=1;
  }
  if(G.isNumber(J)&&(J>-1)&&(J<this.getLength())){
    var L=this.getRecords(J,H);
    var I=[];
    for(var K=0;K<L.length;K++){
      I[I.length]=E.DataTable._cloneObject(L[K]);
    }
    this._deleteRecord(J,H);
    this.fireEvent("recordsDeleteEvent",{
      data:I,
      index:J
    });
    return I;
  }else{
    return null;
  }
},
reset:function(){
  this._records=[];
  this.fireEvent("resetEvent");
}
};

G.augmentProto(B,A.EventProvider);
YAHOO.widget.Record=function(H){
  this._nCount=E.Record._nCount;
  this._sId="yui-rec"+this._nCount;
  E.Record._nCount++;
  this._oData={};

  if(G.isObject(H)){
    for(var I in H){
      if(G.hasOwnProperty(H,I)){
        this._oData[I]=H[I];
      }
    }
    }
  };

YAHOO.widget.Record._nCount=0;
YAHOO.widget.Record.prototype={
  _nCount:null,
  _sId:null,
  _oData:null,
  getCount:function(){
    return this._nCount;
  },
  getId:function(){
    return this._sId;
  },
  getData:function(H){
    if(G.isString(H)){
      return this._oData[H];
    }else{
      return this._oData;
    }
  },
setData:function(H,I){
  this._oData[H]=I;
}
};

})();
(function(){
  var H=YAHOO.lang,A=YAHOO.util,E=YAHOO.widget,B=YAHOO.env.ua,C=A.Dom,G=A.Event,F=A.DataSourceBase;
  YAHOO.widget.DataTable=function(I,M,O,K){
    var L=E.DataTable;
    if(K&&K.scrollable){
      return new YAHOO.widget.ScrollingDataTable(I,M,O,K);
    }
    this._nIndex=L._nCount;
    this._sId="yui-dt"+this._nIndex;
    this._oChainRender=new YAHOO.util.Chain();
    this._oChainRender.subscribe("end",this._onRenderChainEnd,this,true);
    this._initConfigs(K);
    this._initDataSource(O);
    if(!this._oDataSource){
      return;
    }
    this._initColumnSet(M);
    if(!this._oColumnSet){
      return;
    }
    this._initRecordSet();
    if(!this._oRecordSet){}
    L.superclass.constructor.call(this,I,this.configs);
    var Q=this._initDomElements(I);
    if(!Q){
      return;
    }
    this.showTableMessage(this.get("MSG_LOADING"),L.CLASS_LOADING);
    this._initEvents();
    L._nCount++;
    L._nCurrentCount++;
    var N={
      success:this.onDataReturnSetRows,
      failure:this.onDataReturnSetRows,
      scope:this,
      argument:this.getState()
      };

    var P=this.get("initialLoad");
    if(P===true){
      this._oDataSource.sendRequest(this.get("initialRequest"),N);
    }else{
      if(P===false){
        this.showTableMessage(this.get("MSG_EMPTY"),L.CLASS_EMPTY);
      }else{
        var J=P||{};

        N.argument=J.argument||{};

        this._oDataSource.sendRequest(J.request,N);
      }
    }
  };

var D=E.DataTable;
H.augmentObject(D,{
  CLASS_DATATABLE:"yui-dt",
  CLASS_LINER:"yui-dt-liner",
  CLASS_LABEL:"yui-dt-label",
  CLASS_MESSAGE:"yui-dt-message",
  CLASS_MASK:"yui-dt-mask",
  CLASS_DATA:"yui-dt-data",
  CLASS_COLTARGET:"yui-dt-coltarget",
  CLASS_RESIZER:"yui-dt-resizer",
  CLASS_RESIZERLINER:"yui-dt-resizerliner",
  CLASS_RESIZERPROXY:"yui-dt-resizerproxy",
  CLASS_EDITOR:"yui-dt-editor",
  CLASS_PAGINATOR:"yui-dt-paginator",
  CLASS_PAGE:"yui-dt-page",
  CLASS_DEFAULT:"yui-dt-default",
  CLASS_PREVIOUS:"yui-dt-previous",
  CLASS_NEXT:"yui-dt-next",
  CLASS_FIRST:"yui-dt-first",
  CLASS_LAST:"yui-dt-last",
  CLASS_EVEN:"yui-dt-even",
  CLASS_ODD:"yui-dt-odd",
  CLASS_SELECTED:"yui-dt-selected",
  CLASS_HIGHLIGHTED:"yui-dt-highlighted",
  CLASS_HIDDEN:"yui-dt-hidden",
  CLASS_DISABLED:"yui-dt-disabled",
  CLASS_EMPTY:"yui-dt-empty",
  CLASS_LOADING:"yui-dt-loading",
  CLASS_ERROR:"yui-dt-error",
  CLASS_EDITABLE:"yui-dt-editable",
  CLASS_DRAGGABLE:"yui-dt-draggable",
  CLASS_RESIZEABLE:"yui-dt-resizeable",
  CLASS_SCROLLABLE:"yui-dt-scrollable",
  CLASS_SORTABLE:"yui-dt-sortable",
  CLASS_ASC:"yui-dt-asc",
  CLASS_DESC:"yui-dt-desc",
  CLASS_BUTTON:"yui-dt-button",
  CLASS_CHECKBOX:"yui-dt-checkbox",
  CLASS_DROPDOWN:"yui-dt-dropdown",
  CLASS_RADIO:"yui-dt-radio",
  _nCount:0,
  _nCurrentCount:0,
  _elDynStyleNode:null,
  _bDynStylesFallback:(B.ie)?true:false,
  _oDynStyles:{},
  _elColumnDragTarget:null,
  _elColumnResizerProxy:null,
  _cloneObject:function(L){
    if(!H.isValue(L)){
      return L;
    }
    var N={};

    if(L instanceof YAHOO.widget.BaseCellEditor){
      N=L;
    }else{
      if(H.isFunction(L)){
        N=L;
      }else{
        if(H.isArray(L)){
          var M=[];
          for(var K=0,J=L.length;K<J;K++){
            M[K]=D._cloneObject(L[K]);
          }
          N=M;
        }else{
          if(H.isObject(L)){
            for(var I in L){
              if(H.hasOwnProperty(L,I)){
                if(H.isValue(L[I])&&H.isObject(L[I])||H.isArray(L[I])){
                  N[I]=D._cloneObject(L[I]);
                }else{
                  N[I]=L[I];
                }
              }
            }
            }else{
      N=L;
    }
  }
}
}
return N;
},
_destroyColumnDragTargetEl:function(){
  if(D._elColumnDragTarget){
    var I=D._elColumnDragTarget;
    YAHOO.util.Event.purgeElement(I);
    I.parentNode.removeChild(I);
    D._elColumnDragTarget=null;
  }
},
_initColumnDragTargetEl:function(){
  if(!D._elColumnDragTarget){
    var I=document.createElement("div");
    I.className=D.CLASS_COLTARGET;
    I.style.display="none";
    document.body.insertBefore(I,document.body.firstChild);
    D._elColumnDragTarget=I;
  }
  return D._elColumnDragTarget;
},
_destroyColumnResizerProxyEl:function(){
  if(D._elColumnResizerProxy){
    var I=D._elColumnResizerProxy;
    YAHOO.util.Event.purgeElement(I);
    I.parentNode.removeChild(I);
    D._elColumnResizerProxy=null;
  }
},
_initColumnResizerProxyEl:function(){
  if(!D._elColumnResizerProxy){
    var I=document.createElement("div");
    I.id="yui-dt-colresizerproxy";
    I.className=D.CLASS_RESIZERPROXY;
    document.body.insertBefore(I,document.body.firstChild);
    D._elColumnResizerProxy=I;
  }
  return D._elColumnResizerProxy;
},
formatButton:function(I,J,K,M){
  var L=H.isValue(M)?M:"Click";
  I.innerHTML='<button type="button" class="'+D.CLASS_BUTTON+'">'+L+"</button>";
},
formatCheckbox:function(I,J,K,M){
  var L=M;
  L=(L)?' checked="checked"':"";
  I.innerHTML='<input type="checkbox"'+L+' class="'+D.CLASS_CHECKBOX+'" />';
},
formatCurrency:function(I,J,K,L){
  I.innerHTML=A.Number.format(L,K.currencyOptions||this.get("currencyOptions"));
},
formatDate:function(I,K,L,M){
  var J=L.dateOptions||this.get("dateOptions");
  I.innerHTML=A.Date.format(M,J,J.locale);
},
formatDropdown:function(K,R,P,I){
  var Q=(H.isValue(I))?I:R.getData(P.field),S=(H.isArray(P.dropdownOptions))?P.dropdownOptions:null,J,O=K.getElementsByTagName("select");
  if(O.length===0){
    J=document.createElement("select");
    J.className=D.CLASS_DROPDOWN;
    J=K.appendChild(J);
    G.addListener(J,"change",this._onDropdownChange,this);
  }
  J=O[0];
  if(J){
    J.innerHTML="";
    if(S){
      for(var M=0;M<S.length;M++){
        var N=S[M];
        var L=document.createElement("option");
        L.value=(H.isValue(N.value))?N.value:N;
        L.innerHTML=(H.isValue(N.text))?N.text:(H.isValue(N.label))?N.label:N;
        L=J.appendChild(L);
        if(L.value==Q){
          L.selected=true;
        }
      }
      }else{
  J.innerHTML='<option selected value="'+Q+'">'+Q+"</option>";
}
}else{
  K.innerHTML=H.isValue(I)?I:"";
}
},
formatEmail:function(I,J,K,L){
  if(H.isString(L)){
    I.innerHTML='<a href="mailto:'+L+'">'+L+"</a>";
  }else{
    I.innerHTML=H.isValue(L)?L:"";
  }
},
formatLink:function(I,J,K,L){
  if(H.isString(L)){
    I.innerHTML='<a href="'+L+'">'+L+"</a>";
  }else{
    I.innerHTML=H.isValue(L)?L:"";
  }
},
formatNumber:function(I,J,K,L){
  I.innerHTML=A.Number.format(L,K.numberOptions||this.get("numberOptions"));
},
formatRadio:function(I,J,K,M){
  var L=M;
  L=(L)?' checked="checked"':"";
  I.innerHTML='<input type="radio"'+L+' name="'+this.getId()+"-col-"+K.getSanitizedKey()+'"'+' class="'+D.CLASS_RADIO+'" />';
},
formatText:function(I,J,L,M){
  var K=(H.isValue(M))?M:"";
  I.innerHTML=K.toString().replace(/&/g,"&#38;").replace(/</g,"&#60;").replace(/>/g,"&#62;");
},
formatTextarea:function(J,K,M,N){
  var L=(H.isValue(N))?N:"",I="<textarea>"+L+"</textarea>";
  J.innerHTML=I;
},
formatTextbox:function(J,K,M,N){
  var L=(H.isValue(N))?N:"",I='<input type="text" value="'+L+'" />';
  J.innerHTML=I;
},
formatDefault:function(I,J,K,L){
  I.innerHTML=L===undefined||L===null||(typeof L==="number"&&isNaN(L))?"&#160;":L.toString();
},
validateNumber:function(J){
  var I=J*1;
  if(H.isNumber(I)){
    return I;
  }else{
    return undefined;
  }
}
});
D.Formatter={
  button:D.formatButton,
  checkbox:D.formatCheckbox,
  currency:D.formatCurrency,
  "date":D.formatDate,
  dropdown:D.formatDropdown,
  email:D.formatEmail,
  link:D.formatLink,
  "number":D.formatNumber,
  radio:D.formatRadio,
  text:D.formatText,
  textarea:D.formatTextarea,
  textbox:D.formatTextbox,
  defaultFormatter:D.formatDefault
  };

H.extend(D,A.Element,{
  initAttributes:function(I){
    I=I||{};

    D.superclass.initAttributes.call(this,I);
    this.setAttributeConfig("summary",{
      value:"",
      validator:H.isString,
      method:function(J){
        if(this._elTable){
          this._elTable.summary=J;
        }
      }
    });
this.setAttributeConfig("selectionMode",{
  value:"standard",
  validator:H.isString
  });
this.setAttributeConfig("sortedBy",{
  value:null,
  validator:function(J){
    if(J){
      return(H.isObject(J)&&J.key);
    }else{
      return(J===null);
    }
  },
method:function(K){
  var R=this.get("sortedBy");
  this._configs.sortedBy.value=K;
  var J,O,M,Q;
  if(this._elThead){
    if(R&&R.key&&R.dir){
      J=this._oColumnSet.getColumn(R.key);
      O=J.getKeyIndex();
      var U=J.getThEl();
      C.removeClass(U,R.dir);
      this.formatTheadCell(J.getThLinerEl().firstChild,J,K);
    }
    if(K){
      M=(K.column)?K.column:this._oColumnSet.getColumn(K.key);
      Q=M.getKeyIndex();
      var V=M.getThEl();
      if(K.dir&&((K.dir=="asc")||(K.dir=="desc"))){
        var P=(K.dir=="desc")?D.CLASS_DESC:D.CLASS_ASC;
        C.addClass(V,P);
      }else{
        var L=K.dir||D.CLASS_ASC;
        C.addClass(V,L);
      }
      this.formatTheadCell(M.getThLinerEl().firstChild,M,K);
    }
  }
  if(this._elTbody){
  this._elTbody.style.display="none";
  var S=this._elTbody.rows,T;
  for(var N=S.length-1;N>-1;N--){
    T=S[N].childNodes;
    if(T[O]){
      C.removeClass(T[O],R.dir);
    }
    if(T[Q]){
      C.addClass(T[Q],K.dir);
    }
  }
  this._elTbody.style.display="";
}
this._clearTrTemplateEl();
}
});
this.setAttributeConfig("paginator",{
  value:null,
  validator:function(J){
    return J===null||J instanceof E.Paginator;
  },
  method:function(){
    this._updatePaginator.apply(this,arguments);
  }
});
this.setAttributeConfig("caption",{
  value:null,
  validator:H.isString,
  method:function(J){
    this._initCaptionEl(J);
  }
});
this.setAttributeConfig("draggableColumns",{
  value:false,
  validator:H.isBoolean,
  method:function(J){
    if(this._elThead){
      if(J){
        this._initDraggableColumns();
      }else{
        this._destroyDraggableColumns();
      }
    }
  }
});
this.setAttributeConfig("renderLoopSize",{
  value:0,
  validator:H.isNumber
  });
this.setAttributeConfig("formatRow",{
  value:null,
  validator:H.isFunction
  });
this.setAttributeConfig("generateRequest",{
  value:function(K,N){
    K=K||{
      pagination:null,
      sortedBy:null
    };

    var M=encodeURIComponent((K.sortedBy)?K.sortedBy.key:N.getColumnSet().keys[0].getKey());
    var J=(K.sortedBy&&K.sortedBy.dir===YAHOO.widget.DataTable.CLASS_DESC)?"desc":"asc";
    var O=(K.pagination)?K.pagination.recordOffset:0;
    var L=(K.pagination)?K.pagination.rowsPerPage:null;
    return"sort="+M+"&dir="+J+"&startIndex="+O+((L!==null)?"&results="+L:"");
  },
  validator:H.isFunction
  });
this.setAttributeConfig("initialRequest",{
  value:null
});
this.setAttributeConfig("initialLoad",{
  value:true
});
this.setAttributeConfig("dynamicData",{
  value:false,
  validator:H.isBoolean
  });
this.setAttributeConfig("MSG_EMPTY",{
  value:"No records found.",
  validator:H.isString
  });
this.setAttributeConfig("MSG_LOADING",{
  value:"Loading...",
  validator:H.isString
  });
this.setAttributeConfig("MSG_ERROR",{
  value:"Data error.",
  validator:H.isString
  });
this.setAttributeConfig("MSG_SORTASC",{
  value:"Click to sort ascending",
  validator:H.isString,
  method:function(K){
    if(this._elThead){
      for(var L=0,M=this.getColumnSet().keys,J=M.length;L<J;L++){
        if(M[L].sortable&&this.getColumnSortDir(M[L])===D.CLASS_ASC){
          M[L]._elThLabel.firstChild.title=K;
        }
      }
      }
  }
});
this.setAttributeConfig("MSG_SORTDESC",{
  value:"Click to sort descending",
  validator:H.isString,
  method:function(K){
    if(this._elThead){
      for(var L=0,M=this.getColumnSet().keys,J=M.length;L<J;L++){
        if(M[L].sortable&&this.getColumnSortDir(M[L])===D.CLASS_DESC){
          M[L]._elThLabel.firstChild.title=K;
        }
      }
      }
  }
});
this.setAttributeConfig("currencySymbol",{
  value:"$",
  validator:H.isString
  });
this.setAttributeConfig("currencyOptions",{
  value:{
    prefix:this.get("currencySymbol"),
    decimalPlaces:2,
    decimalSeparator:".",
    thousandsSeparator:","
  }
});
this.setAttributeConfig("dateOptions",{
  value:{
    format:"%m/%d/%Y",
    locale:"en"
  }
});
this.setAttributeConfig("numberOptions",{
  value:{
    decimalPlaces:0,
    thousandsSeparator:","
  }
});
},
_bInit:true,
_nIndex:null,
_nTrCount:0,
_nTdCount:0,
_sId:null,
_oChainRender:null,
_elContainer:null,
_elMask:null,
_elTable:null,
_elCaption:null,
_elColgroup:null,
_elThead:null,
_elTbody:null,
_elMsgTbody:null,
_elMsgTr:null,
_elMsgTd:null,
_oDataSource:null,
_oColumnSet:null,
_oRecordSet:null,
_oCellEditor:null,
_sFirstTrId:null,
_sLastTrId:null,
_elTrTemplate:null,
_aDynFunctions:[],
clearTextSelection:function(){
  var I;
  if(window.getSelection){
    I=window.getSelection();
  }else{
    if(document.getSelection){
      I=document.getSelection();
    }else{
      if(document.selection){
        I=document.selection;
      }
    }
  }
if(I){
  if(I.empty){
    I.empty();
  }else{
    if(I.removeAllRanges){
      I.removeAllRanges();
    }else{
      if(I.collapse){
        I.collapse();
      }
    }
  }
}
},
_focusEl:function(I){
  I=I||this._elTbody;
  setTimeout(function(){
    try{
      I.focus();
    }catch(J){}
  },0);
},
_repaintGecko:(B.gecko)?function(J){
  J=J||this._elContainer;
  var I=J.parentNode;
  var K=J.nextSibling;
  I.insertBefore(I.removeChild(J),K);
}:function(){},
_repaintOpera:(B.opera)?function(){
  if(B.opera){
    document.documentElement.className+=" ";
    document.documentElement.className=YAHOO.lang.trim(document.documentElement.className);
  }
}:function(){},
_repaintWebkit:(B.webkit)?function(J){
  J=J||this._elContainer;
  var I=J.parentNode;
  var K=J.nextSibling;
  I.insertBefore(I.removeChild(J),K);
}:function(){},
_initConfigs:function(I){
  if(!I||!H.isObject(I)){
    I={};

}
this.configs=I;
},
_initColumnSet:function(M){
  var L,J,I;
  if(this._oColumnSet){
    for(J=0,I=this._oColumnSet.keys.length;J<I;J++){
      L=this._oColumnSet.keys[J];
      D._oDynStyles["."+this.getId()+"-col-"+L.getSanitizedKey()+" ."+D.CLASS_LINER]=undefined;
      if(L.editor&&L.editor.unsubscribeAll){
        L.editor.unsubscribeAll();
      }
    }
    this._oColumnSet=null;
  this._clearTrTemplateEl();
}
if(H.isArray(M)){
  this._oColumnSet=new YAHOO.widget.ColumnSet(M);
}else{
  if(M instanceof YAHOO.widget.ColumnSet){
    this._oColumnSet=M;
  }
}
var K=this._oColumnSet.keys;
for(J=0,I=K.length;J<I;J++){
  L=K[J];
  if(L.editor&&L.editor.subscribe){
    L.editor.subscribe("showEvent",this._onEditorShowEvent,this,true);
    L.editor.subscribe("keydownEvent",this._onEditorKeydownEvent,this,true);
    L.editor.subscribe("revertEvent",this._onEditorRevertEvent,this,true);
    L.editor.subscribe("saveEvent",this._onEditorSaveEvent,this,true);
    L.editor.subscribe("cancelEvent",this._onEditorCancelEvent,this,true);
    L.editor.subscribe("blurEvent",this._onEditorBlurEvent,this,true);
    L.editor.subscribe("blockEvent",this._onEditorBlockEvent,this,true);
    L.editor.subscribe("unblockEvent",this._onEditorUnblockEvent,this,true);
  }
}
},
_initDataSource:function(I){
  this._oDataSource=null;
  if(I&&(H.isFunction(I.sendRequest))){
    this._oDataSource=I;
  }else{
    var J=null;
    var N=this._elContainer;
    var K=0;
    if(N.hasChildNodes()){
      var M=N.childNodes;
      for(K=0;K<M.length;K++){
        if(M[K].nodeName&&M[K].nodeName.toLowerCase()=="table"){
          J=M[K];
          break;
        }
      }
      if(J){
      var L=[];
      for(;K<this._oColumnSet.keys.length;K++){
        L.push({
          key:this._oColumnSet.keys[K].key
          });
      }
      this._oDataSource=new F(J);
      this._oDataSource.responseType=F.TYPE_HTMLTABLE;
      this._oDataSource.responseSchema={
        fields:L
      };

  }
}
}
},
_initRecordSet:function(){
  if(this._oRecordSet){
    this._oRecordSet.reset();
  }else{
    this._oRecordSet=new YAHOO.widget.RecordSet();
  }
},
_initDomElements:function(I){
  this._initContainerEl(I);
  this._initTableEl(this._elContainer);
  this._initColgroupEl(this._elTable);
  this._initTheadEl(this._elTable);
  this._initMsgTbodyEl(this._elTable);
  this._initTbodyEl(this._elTable);
  if(!this._elContainer||!this._elTable||!this._elColgroup||!this._elThead||!this._elTbody||!this._elMsgTbody){
    return false;
  }else{
    return true;
  }
},
_destroyContainerEl:function(I){
  C.removeClass(I,D.CLASS_DATATABLE);
  G.purgeElement(I,true);
  I.innerHTML="";
  this._elContainer=null;
  this._elColgroup=null;
  this._elThead=null;
  this._elTbody=null;
},
_initContainerEl:function(J){
  J=C.get(J);
  if(J&&J.nodeName&&(J.nodeName.toLowerCase()=="div")){
    this._destroyContainerEl(J);
    C.addClass(J,D.CLASS_DATATABLE);
    G.addListener(J,"focus",this._onTableFocus,this);
    G.addListener(J,"dblclick",this._onTableDblclick,this);
    this._elContainer=J;
    var I=document.createElement("div");
    I.className=D.CLASS_MASK;
    I.style.display="none";
    this._elMask=J.appendChild(I);
  }
},
_destroyTableEl:function(){
  var I=this._elTable;
  if(I){
    G.purgeElement(I,true);
    I.parentNode.removeChild(I);
    this._elCaption=null;
    this._elColgroup=null;
    this._elThead=null;
    this._elTbody=null;
  }
},
_initCaptionEl:function(I){
  if(this._elTable&&I){
    if(!this._elCaption){
      this._elCaption=this._elTable.createCaption();
    }
    this._elCaption.innerHTML=I;
  }else{
    if(this._elCaption){
      this._elCaption.parentNode.removeChild(this._elCaption);
    }
  }
},
_initTableEl:function(I){
  if(I){
    this._destroyTableEl();
    this._elTable=I.appendChild(document.createElement("table"));
    this._elTable.summary=this.get("summary");
    if(this.get("caption")){
      this._initCaptionEl(this.get("caption"));
    }
  }
},
_destroyColgroupEl:function(){
  var I=this._elColgroup;
  if(I){
    var J=I.parentNode;
    G.purgeElement(I,true);
    J.removeChild(I);
    this._elColgroup=null;
  }
},
_initColgroupEl:function(R){
  if(R){
    this._destroyColgroupEl();
    var K=this._aColIds||[],Q=this._oColumnSet.keys,L=0,O=K.length,I,N,P=document.createDocumentFragment(),M=document.createElement("col");
    for(L=0,O=Q.length;L<O;L++){
      N=Q[L];
      I=P.appendChild(M.cloneNode(false));
    }
    var J=R.insertBefore(document.createElement("colgroup"),R.firstChild);
    J.appendChild(P);
    this._elColgroup=J;
  }
},
_insertColgroupColEl:function(I){
  if(H.isNumber(I)&&this._elColgroup){
    var J=this._elColgroup.childNodes[I]||null;
    this._elColgroup.insertBefore(document.createElement("col"),J);
  }
},
_removeColgroupColEl:function(I){
  if(H.isNumber(I)&&this._elColgroup&&this._elColgroup.childNodes[I]){
    this._elColgroup.removeChild(this._elColgroup.childNodes[I]);
  }
},
_reorderColgroupColEl:function(K,J){
  if(H.isArray(K)&&H.isNumber(J)&&this._elColgroup&&(this._elColgroup.childNodes.length>K[K.length-1])){
    var I,M=[];
    for(I=K.length-1;I>-1;I--){
      M.push(this._elColgroup.removeChild(this._elColgroup.childNodes[K[I]]));
    }
    var L=this._elColgroup.childNodes[J]||null;
    for(I=M.length-1;I>-1;I--){
      this._elColgroup.insertBefore(M[I],L);
    }
    }
  },
_destroyTheadEl:function(){
  var J=this._elThead;
  if(J){
    var I=J.parentNode;
    G.purgeElement(J,true);
    this._destroyColumnHelpers();
    I.removeChild(J);
    this._elThead=null;
  }
},
_initTheadEl:function(S){
  S=S||this._elTable;
  if(S){
    this._destroyTheadEl();
    var N=(this._elColgroup)?S.insertBefore(document.createElement("thead"),this._elColgroup.nextSibling):S.appendChild(document.createElement("thead"));
    G.addListener(N,"focus",this._onTheadFocus,this);
    G.addListener(N,"keydown",this._onTheadKeydown,this);
    G.addListener(N,"mouseover",this._onTableMouseover,this);
    G.addListener(N,"mouseout",this._onTableMouseout,this);
    G.addListener(N,"mousedown",this._onTableMousedown,this);
    G.addListener(N,"mouseup",this._onTableMouseup,this);
    G.addListener(N,"click",this._onTheadClick,this);
    var U=this._oColumnSet,Q,O,M,K;
    var T=U.tree;
    var L;
    for(O=0;O<T.length;O++){
      var J=N.appendChild(document.createElement("tr"));
      for(M=0;M<T[O].length;M++){
        Q=T[O][M];
        L=J.appendChild(document.createElement("th"));
        this._initThEl(L,Q);
      }
      if(O===0){
        C.addClass(J,D.CLASS_FIRST);
      }
      if(O===(T.length-1)){
        C.addClass(J,D.CLASS_LAST);
      }
    }
    var I=U.headers[0]||[];
  for(O=0;O<I.length;O++){
    C.addClass(C.get(this.getId()+"-th-"+I[O]),D.CLASS_FIRST);
  }
  var P=U.headers[U.headers.length-1]||[];
  for(O=0;O<P.length;O++){
    C.addClass(C.get(this.getId()+"-th-"+P[O]),D.CLASS_LAST);
  }
  if(B.webkit&&B.webkit<420){
    var R=this;
    setTimeout(function(){
      N.style.display="";
    },0);
    N.style.display="none";
  }
  this._elThead=N;
  this._initColumnHelpers();
}
},
_initThEl:function(M,L){
  M.id=this.getId()+"-th-"+L.getSanitizedKey();
  M.innerHTML="";
  M.rowSpan=L.getRowspan();
  M.colSpan=L.getColspan();
  L._elTh=M;
  var I=M.appendChild(document.createElement("div"));
  I.id=M.id+"-liner";
  I.className=D.CLASS_LINER;
  L._elThLiner=I;
  var J=I.appendChild(document.createElement("span"));
  J.className=D.CLASS_LABEL;
  if(L.abbr){
    M.abbr=L.abbr;
  }
  if(L.hidden){
    this._clearMinWidth(L);
  }
  M.className=this._getColumnClassNames(L);
  if(L.width){
    var K=(L.minWidth&&(L.width<L.minWidth))?L.minWidth:L.width;
    if(D._bDynStylesFallback){
      M.firstChild.style.overflow="hidden";
      M.firstChild.style.width=K+"px";
    }else{
      this._setColumnWidthDynStyles(L,K+"px","hidden");
    }
  }
  this.formatTheadCell(J,L,this.get("sortedBy"));
L._elThLabel=J;
},
formatTheadCell:function(I,M,K){
  var Q=M.getKey();
  var P=H.isValue(M.label)?M.label:Q;
  if(M.sortable){
    var N=this.getColumnSortDir(M,K);
    var J=(N===D.CLASS_DESC);
    if(K&&(M.key===K.key)){
      J=!(K.dir===D.CLASS_DESC);
    }
    var L=this.getId()+"-href-"+M.getSanitizedKey();
    var O=(J)?this.get("MSG_SORTDESC"):this.get("MSG_SORTASC");
    I.innerHTML='<a href="'+L+'" title="'+O+'" class="'+D.CLASS_SORTABLE+'">'+P+"</a>";
  }else{
    I.innerHTML=P;
  }
},
_destroyDraggableColumns:function(){
  var K,L;
  for(var J=0,I=this._oColumnSet.tree[0].length;J<I;J++){
    K=this._oColumnSet.tree[0][J];
    if(K._dd){
      K._dd=K._dd.unreg();
      C.removeClass(K.getThEl(),D.CLASS_DRAGGABLE);
    }
  }
  },
_initDraggableColumns:function(){
  this._destroyDraggableColumns();
  if(A.DD){
    var L,M,J;
    for(var K=0,I=this._oColumnSet.tree[0].length;K<I;K++){
      L=this._oColumnSet.tree[0][K];
      M=L.getThEl();
      C.addClass(M,D.CLASS_DRAGGABLE);
      J=D._initColumnDragTargetEl();
      L._dd=new YAHOO.widget.ColumnDD(this,L,M,J);
    }
    }else{}
},
_destroyResizeableColumns:function(){
  var J=this._oColumnSet.keys;
  for(var K=0,I=J.length;K<I;K++){
    if(J[K]._ddResizer){
      J[K]._ddResizer=J[K]._ddResizer.unreg();
      C.removeClass(J[K].getThEl(),D.CLASS_RESIZEABLE);
    }
  }
  },
_initResizeableColumns:function(){
  this._destroyResizeableColumns();
  if(A.DD){
    var O,J,M,P,I,Q,L;
    for(var K=0,N=this._oColumnSet.keys.length;K<N;K++){
      O=this._oColumnSet.keys[K];
      if(O.resizeable){
        J=O.getThEl();
        C.addClass(J,D.CLASS_RESIZEABLE);
        M=O.getThLinerEl();
        P=J.appendChild(document.createElement("div"));
        P.className=D.CLASS_RESIZERLINER;
        P.appendChild(M);
        I=P.appendChild(document.createElement("div"));
        I.id=J.id+"-resizer";
        I.className=D.CLASS_RESIZER;
        O._elResizer=I;
        Q=D._initColumnResizerProxyEl();
        O._ddResizer=new YAHOO.util.ColumnResizer(this,O,J,I,Q);
        L=function(R){
          G.stopPropagation(R);
        };

        G.addListener(I,"click",L);
      }
    }
    }else{}
},
_destroyColumnHelpers:function(){
  this._destroyDraggableColumns();
  this._destroyResizeableColumns();
},
_initColumnHelpers:function(){
  if(this.get("draggableColumns")){
    this._initDraggableColumns();
  }
  this._initResizeableColumns();
},
_destroyTbodyEl:function(){
  var I=this._elTbody;
  if(I){
    var J=I.parentNode;
    G.purgeElement(I,true);
    J.removeChild(I);
    this._elTbody=null;
  }
},
_initTbodyEl:function(J){
  if(J){
    this._destroyTbodyEl();
    var I=J.appendChild(document.createElement("tbody"));
    I.tabIndex=0;
    I.className=D.CLASS_DATA;
    G.addListener(I,"focus",this._onTbodyFocus,this);
    G.addListener(I,"mouseover",this._onTableMouseover,this);
    G.addListener(I,"mouseout",this._onTableMouseout,this);
    G.addListener(I,"mousedown",this._onTableMousedown,this);
    G.addListener(I,"mouseup",this._onTableMouseup,this);
    G.addListener(I,"keydown",this._onTbodyKeydown,this);
    G.addListener(I,"keypress",this._onTableKeypress,this);
    G.addListener(I,"click",this._onTbodyClick,this);
    if(B.ie){
      I.hideFocus=true;
    }
    this._elTbody=I;
  }
},
_destroyMsgTbodyEl:function(){
  var I=this._elMsgTbody;
  if(I){
    var J=I.parentNode;
    G.purgeElement(I,true);
    J.removeChild(I);
    this._elTbody=null;
  }
},
_initMsgTbodyEl:function(L){
  if(L){
    var K=document.createElement("tbody");
    K.className=D.CLASS_MESSAGE;
    var J=K.appendChild(document.createElement("tr"));
    J.className=D.CLASS_FIRST+" "+D.CLASS_LAST;
    this._elMsgTr=J;
    var M=J.appendChild(document.createElement("td"));
    M.colSpan=this._oColumnSet.keys.length||1;
    M.className=D.CLASS_FIRST+" "+D.CLASS_LAST;
    this._elMsgTd=M;
    K=L.insertBefore(K,this._elTbody);
    var I=M.appendChild(document.createElement("div"));
    I.className=D.CLASS_LINER;
    this._elMsgTbody=K;
    G.addListener(K,"focus",this._onTbodyFocus,this);
    G.addListener(K,"mouseover",this._onTableMouseover,this);
    G.addListener(K,"mouseout",this._onTableMouseout,this);
    G.addListener(K,"mousedown",this._onTableMousedown,this);
    G.addListener(K,"mouseup",this._onTableMouseup,this);
    G.addListener(K,"keydown",this._onTbodyKeydown,this);
    G.addListener(K,"keypress",this._onTableKeypress,this);
    G.addListener(K,"click",this._onTbodyClick,this);
  }
},
_initEvents:function(){
  this._initColumnSort();
  YAHOO.util.Event.addListener(document,"click",this._onDocumentClick,this);
  this.subscribe("paginatorChange",function(){
    this._handlePaginatorChange.apply(this,arguments);
  });
  this.subscribe("initEvent",function(){
    this.renderPaginator();
  });
  this._initCellEditing();
},
_initColumnSort:function(){
  this.subscribe("theadCellClickEvent",this.onEventSortColumn);
  var I=this.get("sortedBy");
  if(I){
    if(I.dir=="desc"){
      this._configs.sortedBy.value.dir=D.CLASS_DESC;
    }else{
      if(I.dir=="asc"){
        this._configs.sortedBy.value.dir=D.CLASS_ASC;
      }
    }
  }
},
_initCellEditing:function(){
  this.subscribe("editorBlurEvent",function(){
    this.onEditorBlurEvent.apply(this,arguments);
  });
  this.subscribe("editorBlockEvent",function(){
    this.onEditorBlockEvent.apply(this,arguments);
  });
  this.subscribe("editorUnblockEvent",function(){
    this.onEditorUnblockEvent.apply(this,arguments);
  });
},
_getColumnClassNames:function(L,K){
  var I;
  if(H.isString(L.className)){
    I=[L.className];
  }else{
    if(H.isArray(L.className)){
      I=L.className;
    }else{
      I=[];
    }
  }
  I[I.length]=this.getId()+"-col-"+L.getSanitizedKey();
I[I.length]="yui-dt-col-"+L.getSanitizedKey();
var J=this.get("sortedBy")||{};

if(L.key===J.key){
  I[I.length]=J.dir||"";
}
if(L.hidden){
  I[I.length]=D.CLASS_HIDDEN;
}
if(L.selected){
  I[I.length]=D.CLASS_SELECTED;
}
if(L.sortable){
  I[I.length]=D.CLASS_SORTABLE;
}
if(L.resizeable){
  I[I.length]=D.CLASS_RESIZEABLE;
}
if(L.editor){
  I[I.length]=D.CLASS_EDITABLE;
}
if(K){
  I=I.concat(K);
}
return I.join(" ");
},
_clearTrTemplateEl:function(){
  this._elTrTemplate=null;
},
_getTrTemplateEl:function(T,N){
  if(this._elTrTemplate){
    return this._elTrTemplate;
  }else{
    var P=document,R=P.createElement("tr"),K=P.createElement("td"),J=P.createElement("div");
    K.appendChild(J);
    var S=document.createDocumentFragment(),Q=this._oColumnSet.keys,M;
    var O;
    for(var L=0,I=Q.length;L<I;L++){
      M=K.cloneNode(true);
      M=this._formatTdEl(Q[L],M,L,(L===I-1));
      S.appendChild(M);
    }
    R.appendChild(S);
    this._elTrTemplate=R;
    return R;
  }
},
_formatTdEl:function(M,O,P,L){
  var S=this._oColumnSet;
  var I=S.headers,J=I[P],N="",U;
  for(var K=0,T=J.length;K<T;K++){
    U=this._sId+"-th-"+J[K]+" ";
    N+=U;
  }
  O.headers=N;
  var R=[];
  if(P===0){
    R[R.length]=D.CLASS_FIRST;
  }
  if(L){
    R[R.length]=D.CLASS_LAST;
  }
  O.className=this._getColumnClassNames(M,R);
  O.firstChild.className=D.CLASS_LINER;
  if(M.width&&D._bDynStylesFallback){
    var Q=(M.minWidth&&(M.width<M.minWidth))?M.minWidth:M.width;
    O.firstChild.style.overflow="hidden";
    O.firstChild.style.width=Q+"px";
  }
  return O;
},
_addTrEl:function(K){
  var J=this._getTrTemplateEl();
  var I=J.cloneNode(true);
  return this._updateTrEl(I,K);
},
_updateTrEl:function(J,N){
  var M=this.get("formatRow")?this.get("formatRow").call(this,J,N):true;
  if(M){
    J.style.display="none";
    var O=J.childNodes,K;
    for(var L=0,I=O.length;L<I;++L){
      K=O[L];
      this.formatCell(O[L].firstChild,N,this._oColumnSet.keys[L]);
    }
    J.style.display="";
  }
  J.id=N.getId();
  return J;
},
_deleteTrEl:function(I){
  var J;
  if(!H.isNumber(I)){
    J=C.get(I).sectionRowIndex;
  }else{
    J=I;
  }
  if(H.isNumber(J)&&(J>-2)&&(J<this._elTbody.rows.length)){
    return this._elTbody.removeChild(this.getTrEl(I));
  }else{
    return null;
  }
},
_unsetFirstRow:function(){
  if(this._sFirstTrId){
    C.removeClass(this._sFirstTrId,D.CLASS_FIRST);
    this._sFirstTrId=null;
  }
},
_setFirstRow:function(){
  this._unsetFirstRow();
  var I=this.getFirstTrEl();
  if(I){
    C.addClass(I,D.CLASS_FIRST);
    this._sFirstTrId=I.id;
  }
},
_unsetLastRow:function(){
  if(this._sLastTrId){
    C.removeClass(this._sLastTrId,D.CLASS_LAST);
    this._sLastTrId=null;
  }
},
_setLastRow:function(){
  this._unsetLastRow();
  var I=this.getLastTrEl();
  if(I){
    C.addClass(I,D.CLASS_LAST);
    this._sLastTrId=I.id;
  }
},
_setRowStripes:function(S,K){
  var L=this._elTbody.rows,P=0,R=L.length,O=[],Q=0,M=[],I=0;
  if((S!==null)&&(S!==undefined)){
    var N=this.getTrEl(S);
    if(N){
      P=N.sectionRowIndex;
      if(H.isNumber(K)&&(K>1)){
        R=P+K;
      }
    }
  }
for(var J=P;J<R;J++){
  if(J%2){
    O[Q++]=L[J];
  }else{
    M[I++]=L[J];
  }
}
if(O.length){
  C.replaceClass(O,D.CLASS_EVEN,D.CLASS_ODD);
}
if(M.length){
  C.replaceClass(M,D.CLASS_ODD,D.CLASS_EVEN);
}
},
_setSelections:function(){
  var K=this.getSelectedRows();
  var M=this.getSelectedCells();
  if((K.length>0)||(M.length>0)){
    var L=this._oColumnSet,J;
    for(var I=0;I<K.length;I++){
      J=C.get(K[I]);
      if(J){
        C.addClass(J,D.CLASS_SELECTED);
      }
    }
    for(I=0;I<M.length;I++){
    J=C.get(M[I].recordId);
    if(J){
      C.addClass(J.childNodes[L.getColumn(M[I].columnKey).getKeyIndex()],D.CLASS_SELECTED);
    }
  }
  }
},
_onRenderChainEnd:function(){
  this.hideTableMessage();
  if(this._elTbody.rows.length===0){
    this.showTableMessage(this.get("MSG_EMPTY"),D.CLASS_EMPTY);
  }
  var I=this;
  setTimeout(function(){
    if((I instanceof D)&&I._sId){
      if(I._bInit){
        I._bInit=false;
        I.fireEvent("initEvent");
      }
      I.fireEvent("renderEvent");
      I.fireEvent("refreshEvent");
      I.validateColumnWidths();
      I.fireEvent("postRenderEvent");
    }
  },0);
},
_onDocumentClick:function(L,J){
  var M=G.getTarget(L);
  var I=M.nodeName.toLowerCase();
  if(!C.isAncestor(J._elContainer,M)){
    J.fireEvent("tableBlurEvent");
    if(J._oCellEditor){
      if(J._oCellEditor.getContainerEl){
        var K=J._oCellEditor.getContainerEl();
        if(!C.isAncestor(K,M)&&(K.id!==M.id)){
          J._oCellEditor.fireEvent("blurEvent",{
            editor:J._oCellEditor
            });
        }
      }else{
      if(J._oCellEditor.isActive){
        if(!C.isAncestor(J._oCellEditor.container,M)&&(J._oCellEditor.container.id!==M.id)){
          J.fireEvent("editorBlurEvent",{
            editor:J._oCellEditor
            });
        }
      }
    }
}
}
},
_onTableFocus:function(J,I){
  I.fireEvent("tableFocusEvent");
},
_onTheadFocus:function(J,I){
  I.fireEvent("theadFocusEvent");
  I.fireEvent("tableFocusEvent");
},
_onTbodyFocus:function(J,I){
  I.fireEvent("tbodyFocusEvent");
  I.fireEvent("tableFocusEvent");
},
_onTableMouseover:function(L,J){
  var M=G.getTarget(L);
  var I=M.nodeName.toLowerCase();
  var K=true;
  while(M&&(I!="table")){
    switch(I){
      case"body":
        return;
      case"a":
        break;
      case"td":
        K=J.fireEvent("cellMouseoverEvent",{
        target:M,
        event:L
      });
      break;
      case"span":
        if(C.hasClass(M,D.CLASS_LABEL)){
        K=J.fireEvent("theadLabelMouseoverEvent",{
          target:M,
          event:L
        });
        K=J.fireEvent("headerLabelMouseoverEvent",{
          target:M,
          event:L
        });
      }
      break;
      case"th":
        K=J.fireEvent("theadCellMouseoverEvent",{
        target:M,
        event:L
      });
      K=J.fireEvent("headerCellMouseoverEvent",{
        target:M,
        event:L
      });
      break;
      case"tr":
        if(M.parentNode.nodeName.toLowerCase()=="thead"){
        K=J.fireEvent("theadRowMouseoverEvent",{
          target:M,
          event:L
        });
        K=J.fireEvent("headerRowMouseoverEvent",{
          target:M,
          event:L
        });
      }else{
        K=J.fireEvent("rowMouseoverEvent",{
          target:M,
          event:L
        });
      }
      break;
      default:
        break;
    }
    if(K===false){
      return;
    }else{
      M=M.parentNode;
      if(M){
        I=M.nodeName.toLowerCase();
      }
    }
  }
J.fireEvent("tableMouseoverEvent",{
  target:(M||J._elContainer),
  event:L
});
},
_onTableMouseout:function(L,J){
  var M=G.getTarget(L);
  var I=M.nodeName.toLowerCase();
  var K=true;
  while(M&&(I!="table")){
    switch(I){
      case"body":
        return;
      case"a":
        break;
      case"td":
        K=J.fireEvent("cellMouseoutEvent",{
        target:M,
        event:L
      });
      break;
      case"span":
        if(C.hasClass(M,D.CLASS_LABEL)){
        K=J.fireEvent("theadLabelMouseoutEvent",{
          target:M,
          event:L
        });
        K=J.fireEvent("headerLabelMouseoutEvent",{
          target:M,
          event:L
        });
      }
      break;
      case"th":
        K=J.fireEvent("theadCellMouseoutEvent",{
        target:M,
        event:L
      });
      K=J.fireEvent("headerCellMouseoutEvent",{
        target:M,
        event:L
      });
      break;
      case"tr":
        if(M.parentNode.nodeName.toLowerCase()=="thead"){
        K=J.fireEvent("theadRowMouseoutEvent",{
          target:M,
          event:L
        });
        K=J.fireEvent("headerRowMouseoutEvent",{
          target:M,
          event:L
        });
      }else{
        K=J.fireEvent("rowMouseoutEvent",{
          target:M,
          event:L
        });
      }
      break;
      default:
        break;
    }
    if(K===false){
      return;
    }else{
      M=M.parentNode;
      if(M){
        I=M.nodeName.toLowerCase();
      }
    }
  }
J.fireEvent("tableMouseoutEvent",{
  target:(M||J._elContainer),
  event:L
});
},
_onTableMousedown:function(L,J){
  var M=G.getTarget(L);
  var I=M.nodeName.toLowerCase();
  var K=true;
  while(M&&(I!="table")){
    switch(I){
      case"body":
        return;
      case"a":
        break;
      case"td":
        K=J.fireEvent("cellMousedownEvent",{
        target:M,
        event:L
      });
      break;
      case"span":
        if(C.hasClass(M,D.CLASS_LABEL)){
        K=J.fireEvent("theadLabelMousedownEvent",{
          target:M,
          event:L
        });
        K=J.fireEvent("headerLabelMousedownEvent",{
          target:M,
          event:L
        });
      }
      break;
      case"th":
        K=J.fireEvent("theadCellMousedownEvent",{
        target:M,
        event:L
      });
      K=J.fireEvent("headerCellMousedownEvent",{
        target:M,
        event:L
      });
      break;
      case"tr":
        if(M.parentNode.nodeName.toLowerCase()=="thead"){
        K=J.fireEvent("theadRowMousedownEvent",{
          target:M,
          event:L
        });
        K=J.fireEvent("headerRowMousedownEvent",{
          target:M,
          event:L
        });
      }else{
        K=J.fireEvent("rowMousedownEvent",{
          target:M,
          event:L
        });
      }
      break;
      default:
        break;
    }
    if(K===false){
      return;
    }else{
      M=M.parentNode;
      if(M){
        I=M.nodeName.toLowerCase();
      }
    }
  }
J.fireEvent("tableMousedownEvent",{
  target:(M||J._elContainer),
  event:L
});
},
_onTableMouseup:function(L,J){
  var M=G.getTarget(L);
  var I=M.nodeName.toLowerCase();
  var K=true;
  while(M&&(I!="table")){
    switch(I){
      case"body":
        return;
      case"a":
        break;
      case"td":
        K=J.fireEvent("cellMouseupEvent",{
        target:M,
        event:L
      });
      break;
      case"span":
        if(C.hasClass(M,D.CLASS_LABEL)){
        K=J.fireEvent("theadLabelMouseupEvent",{
          target:M,
          event:L
        });
        K=J.fireEvent("headerLabelMouseupEvent",{
          target:M,
          event:L
        });
      }
      break;
      case"th":
        K=J.fireEvent("theadCellMouseupEvent",{
        target:M,
        event:L
      });
      K=J.fireEvent("headerCellMouseupEvent",{
        target:M,
        event:L
      });
      break;
      case"tr":
        if(M.parentNode.nodeName.toLowerCase()=="thead"){
        K=J.fireEvent("theadRowMouseupEvent",{
          target:M,
          event:L
        });
        K=J.fireEvent("headerRowMouseupEvent",{
          target:M,
          event:L
        });
      }else{
        K=J.fireEvent("rowMouseupEvent",{
          target:M,
          event:L
        });
      }
      break;
      default:
        break;
    }
    if(K===false){
      return;
    }else{
      M=M.parentNode;
      if(M){
        I=M.nodeName.toLowerCase();
      }
    }
  }
J.fireEvent("tableMouseupEvent",{
  target:(M||J._elContainer),
  event:L
});
},
_onTableDblclick:function(L,J){
  var M=G.getTarget(L);
  var I=M.nodeName.toLowerCase();
  var K=true;
  while(M&&(I!="table")){
    switch(I){
      case"body":
        return;
      case"td":
        K=J.fireEvent("cellDblclickEvent",{
        target:M,
        event:L
      });
      break;
      case"span":
        if(C.hasClass(M,D.CLASS_LABEL)){
        K=J.fireEvent("theadLabelDblclickEvent",{
          target:M,
          event:L
        });
        K=J.fireEvent("headerLabelDblclickEvent",{
          target:M,
          event:L
        });
      }
      break;
      case"th":
        K=J.fireEvent("theadCellDblclickEvent",{
        target:M,
        event:L
      });
      K=J.fireEvent("headerCellDblclickEvent",{
        target:M,
        event:L
      });
      break;
      case"tr":
        if(M.parentNode.nodeName.toLowerCase()=="thead"){
        K=J.fireEvent("theadRowDblclickEvent",{
          target:M,
          event:L
        });
        K=J.fireEvent("headerRowDblclickEvent",{
          target:M,
          event:L
        });
      }else{
        K=J.fireEvent("rowDblclickEvent",{
          target:M,
          event:L
        });
      }
      break;
      default:
        break;
    }
    if(K===false){
      return;
    }else{
      M=M.parentNode;
      if(M){
        I=M.nodeName.toLowerCase();
      }
    }
  }
J.fireEvent("tableDblclickEvent",{
  target:(M||J._elContainer),
  event:L
});
},
_onTheadKeydown:function(L,J){
  var M=G.getTarget(L);
  var I=M.nodeName.toLowerCase();
  var K=true;
  while(M&&(I!="table")){
    switch(I){
      case"body":
        return;
      case"input":case"textarea":
        break;
      case"thead":
        K=J.fireEvent("theadKeyEvent",{
        target:M,
        event:L
      });
      break;
      default:
        break;
    }
    if(K===false){
      return;
    }else{
      M=M.parentNode;
      if(M){
        I=M.nodeName.toLowerCase();
      }
    }
  }
J.fireEvent("tableKeyEvent",{
  target:(M||J._elContainer),
  event:L
});
},
_onTbodyKeydown:function(M,K){
  var J=K.get("selectionMode");
  if(J=="standard"){
    K._handleStandardSelectionByKey(M);
  }else{
    if(J=="single"){
      K._handleSingleSelectionByKey(M);
    }else{
      if(J=="cellblock"){
        K._handleCellBlockSelectionByKey(M);
      }else{
        if(J=="cellrange"){
          K._handleCellRangeSelectionByKey(M);
        }else{
          if(J=="singlecell"){
            K._handleSingleCellSelectionByKey(M);
          }
        }
      }
  }
}
if(K._oCellEditor){
  if(K._oCellEditor.fireEvent){
    K._oCellEditor.fireEvent("blurEvent",{
      editor:K._oCellEditor
      });
  }else{
    if(K._oCellEditor.isActive){
      K.fireEvent("editorBlurEvent",{
        editor:K._oCellEditor
        });
    }
  }
}
var N=G.getTarget(M);
var I=N.nodeName.toLowerCase();
var L=true;
while(N&&(I!="table")){
  switch(I){
    case"body":
      return;
    case"tbody":
      L=K.fireEvent("tbodyKeyEvent",{
      target:N,
      event:M
    });
    break;
    default:
      break;
  }
  if(L===false){
    return;
  }else{
    N=N.parentNode;
    if(N){
      I=N.nodeName.toLowerCase();
    }
  }
}
K.fireEvent("tableKeyEvent",{
  target:(N||K._elContainer),
  event:M
});
},
_onTableKeypress:function(K,J){
  if(B.opera||(navigator.userAgent.toLowerCase().indexOf("mac")!==-1)&&(B.webkit<420)){
    var I=G.getCharCode(K);
    if(I==40){
      G.stopEvent(K);
    }else{
      if(I==38){
        G.stopEvent(K);
      }
    }
  }
},
_onTheadClick:function(L,J){
  if(J._oCellEditor){
    if(J._oCellEditor.fireEvent){
      J._oCellEditor.fireEvent("blurEvent",{
        editor:J._oCellEditor
        });
    }else{
      if(J._oCellEditor.isActive){
        J.fireEvent("editorBlurEvent",{
          editor:J._oCellEditor
          });
      }
    }
  }
var M=G.getTarget(L),I=M.nodeName.toLowerCase(),K=true;
while(M&&(I!="table")){
  switch(I){
    case"body":
      return;
    case"input":
      var N=M.type.toLowerCase();
      if(N=="checkbox"){
      K=J.fireEvent("theadCheckboxClickEvent",{
        target:M,
        event:L
      });
    }else{
      if(N=="radio"){
        K=J.fireEvent("theadRadioClickEvent",{
          target:M,
          event:L
        });
      }else{
        if((N=="button")||(N=="image")||(N=="submit")||(N=="reset")){
          K=J.fireEvent("theadButtonClickEvent",{
            target:M,
            event:L
          });
        }
      }
    }
    break;
case"a":
  K=J.fireEvent("theadLinkClickEvent",{
  target:M,
  event:L
});
break;
case"button":
  K=J.fireEvent("theadButtonClickEvent",{
  target:M,
  event:L
});
break;
case"span":
  if(C.hasClass(M,D.CLASS_LABEL)){
  K=J.fireEvent("theadLabelClickEvent",{
    target:M,
    event:L
  });
  K=J.fireEvent("headerLabelClickEvent",{
    target:M,
    event:L
  });
}
break;
case"th":
  K=J.fireEvent("theadCellClickEvent",{
  target:M,
  event:L
});
K=J.fireEvent("headerCellClickEvent",{
  target:M,
  event:L
});
break;
case"tr":
  K=J.fireEvent("theadRowClickEvent",{
  target:M,
  event:L
});
K=J.fireEvent("headerRowClickEvent",{
  target:M,
  event:L
});
break;
default:
  break;
}
if(K===false){
  return;
}else{
  M=M.parentNode;
  if(M){
    I=M.nodeName.toLowerCase();
  }
}
}
J.fireEvent("tableClickEvent",{
  target:(M||J._elContainer),
  event:L
});
},
_onTbodyClick:function(L,J){
  if(J._oCellEditor){
    if(J._oCellEditor.fireEvent){
      J._oCellEditor.fireEvent("blurEvent",{
        editor:J._oCellEditor
        });
    }else{
      if(J._oCellEditor.isActive){
        J.fireEvent("editorBlurEvent",{
          editor:J._oCellEditor
          });
      }
    }
  }
var M=G.getTarget(L),I=M.nodeName.toLowerCase(),K=true;
while(M&&(I!="table")){
  switch(I){
    case"body":
      return;
    case"input":
      var N=M.type.toLowerCase();
      if(N=="checkbox"){
      K=J.fireEvent("checkboxClickEvent",{
        target:M,
        event:L
      });
    }else{
      if(N=="radio"){
        K=J.fireEvent("radioClickEvent",{
          target:M,
          event:L
        });
      }else{
        if((N=="button")||(N=="image")||(N=="submit")||(N=="reset")){
          K=J.fireEvent("buttonClickEvent",{
            target:M,
            event:L
          });
        }
      }
    }
    break;
case"a":
  K=J.fireEvent("linkClickEvent",{
  target:M,
  event:L
});
break;
case"button":
  K=J.fireEvent("buttonClickEvent",{
  target:M,
  event:L
});
break;
case"td":
  K=J.fireEvent("cellClickEvent",{
  target:M,
  event:L
});
break;
case"tr":
  K=J.fireEvent("rowClickEvent",{
  target:M,
  event:L
});
break;
default:
  break;
}
if(K===false){
  return;
}else{
  M=M.parentNode;
  if(M){
    I=M.nodeName.toLowerCase();
  }
}
}
J.fireEvent("tableClickEvent",{
  target:(M||J._elContainer),
  event:L
});
},
_onDropdownChange:function(J,I){
  var K=G.getTarget(J);
  I.fireEvent("dropdownChangeEvent",{
    event:J,
    target:K
  });
},
configs:null,
getId:function(){
  return this._sId;
},
toString:function(){
  return"DataTable instance "+this._sId;
},
getDataSource:function(){
  return this._oDataSource;
},
getColumnSet:function(){
  return this._oColumnSet;
},
getRecordSet:function(){
  return this._oRecordSet;
},
getState:function(){
  return{
    totalRecords:this.get("paginator")?this.get("paginator").get("totalRecords"):this._oRecordSet.getLength(),
    pagination:this.get("paginator")?this.get("paginator").getState():null,
    sortedBy:this.get("sortedBy"),
    selectedRows:this.getSelectedRows(),
    selectedCells:this.getSelectedCells()
    };

},
getContainerEl:function(){
  return this._elContainer;
},
getTableEl:function(){
  return this._elTable;
},
getTheadEl:function(){
  return this._elThead;
},
getTbodyEl:function(){
  return this._elTbody;
},
getMsgTbodyEl:function(){
  return this._elMsgTbody;
},
getMsgTdEl:function(){
  return this._elMsgTd;
},
getTrEl:function(K){
  if(K instanceof YAHOO.widget.Record){
    return document.getElementById(K.getId());
  }else{
    if(H.isNumber(K)){
      var J=this._elTbody.rows;
      return((K>-1)&&(K<J.length))?J[K]:null;
    }else{
      var I=(H.isString(K))?document.getElementById(K):K;
      if(I&&(I.ownerDocument==document)){
        if(I.nodeName.toLowerCase()!="tr"){
          I=C.getAncestorByTagName(I,"tr");
        }
        return I;
      }
    }
  }
return null;
},
getFirstTrEl:function(){
  return this._elTbody.rows[0]||null;
},
getLastTrEl:function(){
  var I=this._elTbody.rows;
  if(I.length>0){
    return I[I.length-1]||null;
  }
},
getNextTrEl:function(K){
  var I=this.getTrIndex(K);
  if(I!==null){
    var J=this._elTbody.rows;
    if(I<J.length-1){
      return J[I+1];
    }
  }
  return null;
},
getPreviousTrEl:function(K){
  var I=this.getTrIndex(K);
  if(I!==null){
    var J=this._elTbody.rows;
    if(I>0){
      return J[I-1];
    }
  }
  return null;
},
getTdLinerEl:function(I){
  var J=this.getTdEl(I);
  return J.firstChild||null;
},
getTdEl:function(I){
  var N;
  var L=C.get(I);
  if(L&&(L.ownerDocument==document)){
    if(L.nodeName.toLowerCase()!="td"){
      N=C.getAncestorByTagName(L,"td");
    }
    else{
      N=L;
    }
    if(N&&((N.parentNode.parentNode==this._elTbody)||(N.parentNode.parentNode===null))){
      return N;
    }
  }else{
  if(I){
    var M,K;
    if(H.isString(I.columnKey)&&H.isString(I.recordId)){
      M=this.getRecord(I.recordId);
      var O=this.getColumn(I.columnKey);
      if(O){
        K=O.getKeyIndex();
      }
    }
    if(I.record&&I.column&&I.column.getKeyIndex){
    M=I.record;
    K=I.column.getKeyIndex();
  }
  var J=this.getTrEl(M);
  if((K!==null)&&J&&J.cells&&J.cells.length>0){
    return J.cells[K]||null;
  }
}
}
return null;
},
getFirstTdEl:function(J){
  var I=this.getTrEl(J)||this.getFirstTrEl();
  if(I&&(I.cells.length>0)){
    return I.cells[0];
  }
  return null;
},
getLastTdEl:function(J){
  var I=this.getTrEl(J)||this.getLastTrEl();
  if(I&&(I.cells.length>0)){
    return I.cells[I.cells.length-1];
  }
  return null;
},
getNextTdEl:function(I){
  var M=this.getTdEl(I);
  if(M){
    var K=M.cellIndex;
    var J=this.getTrEl(M);
    if(K<J.cells.length-1){
      return J.cells[K+1];
    }else{
      var L=this.getNextTrEl(J);
      if(L){
        return L.cells[0];
      }
    }
  }
return null;
},
getPreviousTdEl:function(I){
  var M=this.getTdEl(I);
  if(M){
    var K=M.cellIndex;
    var J=this.getTrEl(M);
    if(K>0){
      return J.cells[K-1];
    }else{
      var L=this.getPreviousTrEl(J);
      if(L){
        return this.getLastTdEl(L);
      }
    }
  }
return null;
},
getAboveTdEl:function(I){
  var K=this.getTdEl(I);
  if(K){
    var J=this.getPreviousTrEl(K);
    if(J){
      return J.cells[K.cellIndex];
    }
  }
  return null;
},
getBelowTdEl:function(I){
  var K=this.getTdEl(I);
  if(K){
    var J=this.getNextTrEl(K);
    if(J){
      return J.cells[K.cellIndex];
    }
  }
  return null;
},
getThLinerEl:function(J){
  var I=this.getColumn(J);
  return(I)?I.getThLinerEl():null;
},
getThEl:function(K){
  var L;
  if(K instanceof YAHOO.widget.Column){
    var J=K;
    L=J.getThEl();
    if(L){
      return L;
    }
  }else{
  var I=C.get(K);
  if(I&&(I.ownerDocument==document)){
    if(I.nodeName.toLowerCase()!="th"){
      L=C.getAncestorByTagName(I,"th");
    }else{
      L=I;
    }
    return L;
  }
}
return null;
},
getTrIndex:function(M){
  var L;
  if(M instanceof YAHOO.widget.Record){
    L=this._oRecordSet.getRecordIndex(M);
    if(L===null){
      return null;
    }
  }else{
  if(H.isNumber(M)){
    L=M;
  }
}
if(H.isNumber(L)){
  if((L>-1)&&(L<this._oRecordSet.getLength())){
    var K=this.get("paginator");
    if(K){
      var J=K.getPageRecords();
      if(J&&L>=J[0]&&L<=J[1]){
        return L-J[0];
      }else{
        return null;
      }
    }else{
    return L;
  }
}else{
  return null;
}
}else{
  var I=this.getTrEl(M);
  if(I&&(I.ownerDocument==document)&&(I.parentNode==this._elTbody)){
    return I.sectionRowIndex;
  }
}
return null;
},
initializeTable:function(){
  this._bInit=true;
  this._oRecordSet.reset();
  var I=this.get("paginator");
  if(I){
    I.set("totalRecords",0);
  }
  this._unselectAllTrEls();
  this._unselectAllTdEls();
  this._aSelections=null;
  this._oAnchorRecord=null;
  this._oAnchorCell=null;
  this.set("sortedBy",null);
},
_runRenderChain:function(){
  this._oChainRender.run();
},
render:function(){
  this._oChainRender.stop();
  this.fireEvent("beforeRenderEvent");
  var O,M,L,P,I;
  var R=this.get("paginator");
  if(R){
    I=this._oRecordSet.getRecords(R.getStartIndex(),R.getRowsPerPage());
  }else{
    I=this._oRecordSet.getRecords();
  }
  var J=this._elTbody,N=this.get("renderLoopSize"),Q=I.length;
  if(Q>0){
    J.style.display="none";
    while(J.lastChild){
      J.removeChild(J.lastChild);
    }
    J.style.display="";
    this._oChainRender.add({
      method:function(U){
        if((this instanceof D)&&this._sId){
          var T=U.nCurrentRecord,W=((U.nCurrentRecord+U.nLoopLength)>Q)?Q:(U.nCurrentRecord+U.nLoopLength),S,V;
          J.style.display="none";
          for(;T<W;T++){
            S=C.get(I[T].getId());
            S=S||this._addTrEl(I[T]);
            V=J.childNodes[T]||null;
            J.insertBefore(S,V);
          }
          J.style.display="";
          U.nCurrentRecord=T;
        }
      },
    scope:this,
    iterations:(N>0)?Math.ceil(Q/N):1,
      argument:{
      nCurrentRecord:0,
      nLoopLength:(N>0)?N:Q
      },
    timeout:(N>0)?0:-1
      });
  this._oChainRender.add({
    method:function(S){
      if((this instanceof D)&&this._sId){
        while(J.rows.length>Q){
          J.removeChild(J.lastChild);
        }
        this._setFirstRow();
        this._setLastRow();
        this._setRowStripes();
        this._setSelections();
      }
    },
  scope:this,
  timeout:(N>0)?0:-1
    });
}else{
  var K=J.rows.length;
  if(K>0){
    this._oChainRender.add({
      method:function(T){
        if((this instanceof D)&&this._sId){
          var S=T.nCurrent,V=T.nLoopLength,U=(S-V<0)?-1:S-V;
          J.style.display="none";
          for(;S>U;S--){
            J.deleteRow(-1);
          }
          J.style.display="";
          T.nCurrent=S;
        }
      },
    scope:this,
    iterations:(N>0)?Math.ceil(K/N):1,
      argument:{
      nCurrent:K,
      nLoopLength:(N>0)?N:K
      },
    timeout:(N>0)?0:-1
      });
}
}
this._runRenderChain();
},
disable:function(){
  var I=this._elTable;
  var J=this._elMask;
  J.style.width=I.offsetWidth+"px";
  J.style.height=I.offsetHeight+"px";
  J.style.display="";
  this.fireEvent("disableEvent");
},
undisable:function(){
  this._elMask.style.display="none";
  this.fireEvent("undisableEvent");
},
destroy:function(){
  var J=this.toString();
  this._oChainRender.stop();
  D._destroyColumnDragTargetEl();
  D._destroyColumnResizerProxyEl();
  this._destroyColumnHelpers();
  var L;
  for(var K=0,I=this._oColumnSet.flat.length;K<I;K++){
    L=this._oColumnSet.flat[K].editor;
    if(L&&L.destroy){
      L.destroy();
      this._oColumnSet.flat[K].editor=null;
    }
  }
  this._destroyPaginator();
this._oRecordSet.unsubscribeAll();
this.unsubscribeAll();
G.removeListener(document,"click",this._onDocumentClick);
this._destroyContainerEl(this._elContainer);
for(var M in this){
  if(H.hasOwnProperty(this,M)){
    this[M]=null;
  }
}
D._nCurrentCount--;
if(D._nCurrentCount<1){
  if(D._elDynStyleNode){
    document.getElementsByTagName("head")[0].removeChild(D._elDynStyleNode);
    D._elDynStyleNode=null;
  }
}
},
showTableMessage:function(J,I){
  var K=this._elMsgTd;
  if(H.isString(J)){
    K.firstChild.innerHTML=J;
  }
  if(H.isString(I)){
    K.className=I;
  }
  this._elMsgTbody.style.display="";
  this.fireEvent("tableMsgShowEvent",{
    html:J,
    className:I
  });
},
hideTableMessage:function(){
  if(this._elMsgTbody.style.display!="none"){
    this._elMsgTbody.style.display="none";
    this._elMsgTbody.parentNode.style.width="";
    this.fireEvent("tableMsgHideEvent");
  }
},
focus:function(){
  this.focusTbodyEl();
},
focusTheadEl:function(){
  this._focusEl(this._elThead);
},
focusTbodyEl:function(){
  this._focusEl(this._elTbody);
},
onShow:function(){
  this.validateColumnWidths();
  for(var L=this._oColumnSet.keys,K=0,I=L.length,J;K<I;K++){
    J=L[K];
    if(J._ddResizer){
      J._ddResizer.resetResizerEl();
    }
  }
  },
getRecordIndex:function(L){
  var K;
  if(!H.isNumber(L)){
    if(L instanceof YAHOO.widget.Record){
      return this._oRecordSet.getRecordIndex(L);
    }else{
      var J=this.getTrEl(L);
      if(J){
        K=J.sectionRowIndex;
      }
    }
  }else{
  K=L;
}
if(H.isNumber(K)){
  var I=this.get("paginator");
  if(I){
    return I.get("recordOffset")+K;
  }else{
    return K;
  }
}
return null;
},
getRecord:function(K){
  var J=this._oRecordSet.getRecord(K);
  if(!J){
    var I=this.getTrEl(K);
    if(I){
      J=this._oRecordSet.getRecord(I.id);
    }
  }
  if(J instanceof YAHOO.widget.Record){
  return this._oRecordSet.getRecord(J);
}else{
  return null;
}
},
getColumn:function(L){
  var N=this._oColumnSet.getColumn(L);
  if(!N){
    var M=this.getTdEl(L);
    if(M){
      N=this._oColumnSet.getColumn(M.cellIndex);
    }else{
      M=this.getThEl(L);
      if(M){
        var J=this._oColumnSet.flat;
        for(var K=0,I=J.length;K<I;K++){
          if(J[K].getThEl().id===M.id){
            N=J[K];
          }
        }
        }
    }
}
if(!N){}
return N;
},
getColumnById:function(I){
  return this._oColumnSet.getColumnById(I);
},
getColumnSortDir:function(K,L){
  if(K.sortOptions&&K.sortOptions.defaultOrder){
    if(K.sortOptions.defaultOrder=="asc"){
      K.sortOptions.defaultDir=D.CLASS_ASC;
    }else{
      if(K.sortOptions.defaultOrder=="desc"){
        K.sortOptions.defaultDir=D.CLASS_DESC;
      }
    }
  }
var J=(K.sortOptions&&K.sortOptions.defaultDir)?K.sortOptions.defaultDir:D.CLASS_ASC;
var I=false;
L=L||this.get("sortedBy");
if(L&&(L.key===K.key)){
  I=true;
  if(L.dir){
    J=(L.dir===D.CLASS_ASC)?D.CLASS_DESC:D.CLASS_ASC;
  }else{
    J=(J===D.CLASS_ASC)?D.CLASS_DESC:D.CLASS_ASC;
  }
}
return J;
},
doBeforeSortColumn:function(J,I){
  this.showTableMessage(this.get("MSG_LOADING"),D.CLASS_LOADING);
  return true;
},
sortColumn:function(N,K){
  if(N&&(N instanceof YAHOO.widget.Column)){
    if(!N.sortable){
      C.addClass(this.getThEl(N),D.CLASS_SORTABLE);
    }
    if(K&&(K!==D.CLASS_ASC)&&(K!==D.CLASS_DESC)){
      K=null;
    }
    var O=K||this.getColumnSortDir(N);
    var M=this.get("sortedBy")||{};

    var U=(M.key===N.key)?true:false;
    var Q=this.doBeforeSortColumn(N,O);
    if(Q){
      if(this.get("dynamicData")){
        var T=this.getState();
        if(T.pagination){
          T.pagination.recordOffset=0;
        }
        T.sortedBy={
          key:N.key,
          dir:O
        };

        var L=this.get("generateRequest")(T,this);
        this.unselectAllRows();
        this.unselectAllCells();
        var S={
          success:this.onDataReturnSetRows,
          failure:this.onDataReturnSetRows,
          argument:T,
          scope:this
        };

        this._oDataSource.sendRequest(L,S);
      }else{
        var I=(N.sortOptions&&H.isFunction(N.sortOptions.sortFunction))?N.sortOptions.sortFunction:null;
        if(!U||K||I){
          var J=YAHOO.util.Sort.compare;
          I=I||function(W,V,Z,Y){
            var X=J(W.getData(Y),V.getData(Y),Z);
            if(X===0){
              return J(W.getCount(),V.getCount(),Z);
            }else{
              return X;
            }
          };

        var R=(N.sortOptions&&N.sortOptions.field)?N.sortOptions.field:N.field;
        this._oRecordSet.sortRecords(I,((O==D.CLASS_DESC)?true:false),R);
      }else{
        this._oRecordSet.reverseRecords();
      }
      var P=this.get("paginator");
      if(P){
        P.setPage(1,true);
      }
      this.render();
      this.set("sortedBy",{
        key:N.key,
        dir:O,
        column:N
      });
    }
    this.fireEvent("columnSortEvent",{
      column:N,
      dir:O
    });
    return;
  }
}
},
setColumnWidth:function(J,I){
  if(!(J instanceof YAHOO.widget.Column)){
    J=this.getColumn(J);
  }
  if(J){
    if(H.isNumber(I)){
      I=(I>J.minWidth)?I:J.minWidth;
      J.width=I;
      this._setColumnWidth(J,I+"px");
      this.fireEvent("columnSetWidthEvent",{
        column:J,
        width:I
      });
    }else{
      if(I===null){
        J.width=I;
        this._setColumnWidth(J,"auto");
        this.validateColumnWidths(J);
        this.fireEvent("columnUnsetWidthEvent",{
          column:J
        });
      }
    }
    this._clearTrTemplateEl();
}else{}
},
_setColumnWidth:function(J,I,K){
  if(J&&(J.getKeyIndex()!==null)){
    K=K||(((I==="")||(I==="auto"))?"visible":"hidden");
    if(!D._bDynStylesFallback){
      this._setColumnWidthDynStyles(J,I,K);
    }else{
      this._setColumnWidthDynFunction(J,I,K);
    }
  }else{}
},
_setColumnWidthDynStyles:function(M,L,N){
  var J=D._elDynStyleNode,K;
  if(!J){
    J=document.createElement("style");
    J.type="text/css";
    J=document.getElementsByTagName("head").item(0).appendChild(J);
    D._elDynStyleNode=J;
  }
  if(J){
    var I="."+this.getId()+"-col-"+M.getSanitizedKey()+" ."+D.CLASS_LINER;
    if(this._elTbody){
      this._elTbody.style.display="none";
    }
    K=D._oDynStyles[I];
    if(!K){
      if(J.styleSheet&&J.styleSheet.addRule){
        J.styleSheet.addRule(I,"overflow:"+N);
        J.styleSheet.addRule(I,"width:"+L);
        K=J.styleSheet.rules[J.styleSheet.rules.length-1];
        D._oDynStyles[I]=K;
      }else{
        if(J.sheet&&J.sheet.insertRule){
          J.sheet.insertRule(I+" {overflow:"+N+";width:"+L+";}",J.sheet.cssRules.length);
          K=J.sheet.cssRules[J.sheet.cssRules.length-1];
          D._oDynStyles[I]=K;
        }
      }
    }else{
  K.style.overflow=N;
  K.style.width=L;
}
if(this._elTbody){
  this._elTbody.style.display="";
}
}
if(!K){
  D._bDynStylesFallback=true;
  this._setColumnWidthDynFunction(M,L);
}
},
_setColumnWidthDynFunction:function(O,J,P){
  if(J=="auto"){
    J="";
  }
  var I=this._elTbody?this._elTbody.rows.length:0;
  if(!this._aDynFunctions[I]){
    var N,M,L;
    var Q=["var colIdx=oColumn.getKeyIndex();","oColumn.getThLinerEl().style.overflow="];
    for(N=I-1,M=2;N>=0;--N){
      Q[M++]="this._elTbody.rows[";
      Q[M++]=N;
      Q[M++]="].cells[colIdx].firstChild.style.overflow=";
    }
    Q[M]="sOverflow;";
    Q[M+1]="oColumn.getThLinerEl().style.width=";
    for(N=I-1,L=M+2;N>=0;--N){
      Q[L++]="this._elTbody.rows[";
      Q[L++]=N;
      Q[L++]="].cells[colIdx].firstChild.style.width=";
    }
    Q[L]="sWidth;";
    this._aDynFunctions[I]=new Function("oColumn","sWidth","sOverflow",Q.join(""));
  }
  var K=this._aDynFunctions[I];
  if(K){
    K.call(this,O,J,P);
  }
},
validateColumnWidths:function(N){
  var K=this._elColgroup;
  var P=K.cloneNode(true);
  var O=false;
  var M=this._oColumnSet.keys;
  var J;
  if(N&&!N.hidden&&!N.width&&(N.getKeyIndex()!==null)){
    J=N.getThLinerEl();
    if((N.minWidth>0)&&(J.offsetWidth<N.minWidth)){
      P.childNodes[N.getKeyIndex()].style.width=N.minWidth+(parseInt(C.getStyle(J,"paddingLeft"),10)|0)+(parseInt(C.getStyle(J,"paddingRight"),10)|0)+"px";
      O=true;
    }else{
      if((N.maxAutoWidth>0)&&(J.offsetWidth>N.maxAutoWidth)){
        this._setColumnWidth(N,N.maxAutoWidth+"px","hidden");
      }
    }
  }else{
  for(var L=0,I=M.length;L<I;L++){
    N=M[L];
    if(!N.hidden&&!N.width){
      J=N.getThLinerEl();
      if((N.minWidth>0)&&(J.offsetWidth<N.minWidth)){
        P.childNodes[L].style.width=N.minWidth+(parseInt(C.getStyle(J,"paddingLeft"),10)|0)+(parseInt(C.getStyle(J,"paddingRight"),10)|0)+"px";
        O=true;
      }else{
        if((N.maxAutoWidth>0)&&(J.offsetWidth>N.maxAutoWidth)){
          this._setColumnWidth(N,N.maxAutoWidth+"px","hidden");
        }
      }
    }
  }
}
if(O){
  K.parentNode.replaceChild(P,K);
  this._elColgroup=P;
}
},
_clearMinWidth:function(I){
  if(I.getKeyIndex()!==null){
    this._elColgroup.childNodes[I.getKeyIndex()].style.width="";
  }
},
_restoreMinWidth:function(I){
  if(I.minWidth&&(I.getKeyIndex()!==null)){
    this._elColgroup.childNodes[I.getKeyIndex()].style.width=I.minWidth+"px";
  }
},
hideColumn:function(N){
  if(!(N instanceof YAHOO.widget.Column)){
    N=this.getColumn(N);
  }
  if(N&&!N.hidden&&N.getTreeIndex()!==null){
    var O=this.getTbodyEl().rows;
    var I=O.length;
    var M=this._oColumnSet.getDescendants(N);
    for(var L=0;L<M.length;L++){
      var K=M[L];
      K.hidden=true;
      C.addClass(K.getThEl(),D.CLASS_HIDDEN);
      var P=K.getKeyIndex();
      if(P!==null){
        this._clearMinWidth(N);
        for(var J=0;J<I;J++){
          C.addClass(O[J].cells[P],D.CLASS_HIDDEN);
        }
        }
        this.fireEvent("columnHideEvent",{
      column:K
    });
    }
    this._repaintOpera();
  this._clearTrTemplateEl();
}else{}
},
showColumn:function(N){
  if(!(N instanceof YAHOO.widget.Column)){
    N=this.getColumn(N);
  }
  if(N&&N.hidden&&(N.getTreeIndex()!==null)){
    var O=this.getTbodyEl().rows;
    var I=O.length;
    var M=this._oColumnSet.getDescendants(N);
    for(var L=0;L<M.length;L++){
      var K=M[L];
      K.hidden=false;
      C.removeClass(K.getThEl(),D.CLASS_HIDDEN);
      var P=K.getKeyIndex();
      if(P!==null){
        this._restoreMinWidth(N);
        for(var J=0;J<I;J++){
          C.removeClass(O[J].cells[P],D.CLASS_HIDDEN);
        }
        }
        this.fireEvent("columnShowEvent",{
      column:K
    });
    }
    this._clearTrTemplateEl();
}else{}
},
removeColumn:function(O){
  if(!(O instanceof YAHOO.widget.Column)){
    O=this.getColumn(O);
  }
  if(O){
    var L=O.getTreeIndex();
    if(L!==null){
      var N,Q,P=O.getKeyIndex();
      if(P===null){
        var T=[];
        var I=this._oColumnSet.getDescendants(O);
        for(N=0,Q=I.length;N<Q;N++){
          var R=I[N].getKeyIndex();
          if(R!==null){
            T[T.length]=R;
          }
        }
        if(T.length>0){
        P=T;
      }
    }else{
    P=[P];
  }
  if(P!==null){
    P.sort(function(V,U){
      return YAHOO.util.Sort.compare(V,U);
    });
    this._destroyTheadEl();
    var J=this._oColumnSet.getDefinitions();
    O=J.splice(L,1)[0];
    this._initColumnSet(J);
    this._initTheadEl();
    for(N=P.length-1;N>-1;N--){
      this._removeColgroupColEl(P[N]);
    }
    var S=this._elTbody.rows;
    if(S.length>0){
      var M=this.get("renderLoopSize"),K=S.length;
      this._oChainRender.add({
        method:function(X){
          if((this instanceof D)&&this._sId){
            var W=X.nCurrentRow,U=M>0?Math.min(W+M,S.length):S.length,Y=X.aIndexes,V;
            for(;W<U;++W){
              for(V=Y.length-1;V>-1;V--){
                S[W].removeChild(S[W].childNodes[Y[V]]);
              }
            }
            X.nCurrentRow=W;
        }
      },
      iterations:(M>0)?Math.ceil(K/M):1,
      argument:{
        nCurrentRow:0,
        aIndexes:P
      },
      scope:this,
      timeout:(M>0)?0:-1
      });
  this._runRenderChain();
}
this.fireEvent("columnRemoveEvent",{
  column:O
});
return O;
}
}
}
},
insertColumn:function(Q,R){
  if(Q instanceof YAHOO.widget.Column){
    Q=Q.getDefinition();
  }else{
    if(Q.constructor!==Object){
      return;
    }
  }
  var W=this._oColumnSet;
if(!H.isValue(R)||!H.isNumber(R)){
  R=W.tree[0].length;
}
this._destroyTheadEl();
var Y=this._oColumnSet.getDefinitions();
Y.splice(R,0,Q);
this._initColumnSet(Y);
this._initTheadEl();
W=this._oColumnSet;
var M=W.tree[0][R];
var O,S,V=[];
var K=W.getDescendants(M);
for(O=0,S=K.length;O<S;O++){
  var T=K[O].getKeyIndex();
  if(T!==null){
    V[V.length]=T;
  }
}
if(V.length>0){
  var X=V.sort(function(c,Z){
    return YAHOO.util.Sort.compare(c,Z);
  })[0];
  for(O=V.length-1;O>-1;O--){
    this._insertColgroupColEl(V[O]);
  }
  var U=this._elTbody.rows;
  if(U.length>0){
    var N=this.get("renderLoopSize"),L=U.length;
    var J=[],P;
    for(O=0,S=V.length;O<S;O++){
      var I=V[O];
      P=this._getTrTemplateEl().childNodes[O].cloneNode(true);
      P=this._formatTdEl(this._oColumnSet.keys[I],P,I,(I===this._oColumnSet.keys.length-1));
      J[I]=P;
    }
    this._oChainRender.add({
      method:function(c){
        if((this instanceof D)&&this._sId){
          var b=c.nCurrentRow,a,e=c.descKeyIndexes,Z=N>0?Math.min(b+N,U.length):U.length,d;
          for(;b<Z;++b){
            d=U[b].childNodes[X]||null;
            for(a=e.length-1;a>-1;a--){
              U[b].insertBefore(c.aTdTemplates[e[a]].cloneNode(true),d);
            }
            }
            c.nCurrentRow=b;
      }
    },
    iterations:(N>0)?Math.ceil(L/N):1,
    argument:{
      nCurrentRow:0,
      aTdTemplates:J,
      descKeyIndexes:V
    },
    scope:this,
    timeout:(N>0)?0:-1
    });
this._runRenderChain();
}
this.fireEvent("columnInsertEvent",{
  column:Q,
  index:R
});
return M;
}
},
reorderColumn:function(P,Q){
  if(!(P instanceof YAHOO.widget.Column)){
    P=this.getColumn(P);
  }
  if(P&&YAHOO.lang.isNumber(Q)){
    var Y=P.getTreeIndex();
    if((Y!==null)&&(Y!==Q)){
      var O,R,K=P.getKeyIndex(),J,U=[],S;
      if(K===null){
        J=this._oColumnSet.getDescendants(P);
        for(O=0,R=J.length;O<R;O++){
          S=J[O].getKeyIndex();
          if(S!==null){
            U[U.length]=S;
          }
        }
        if(U.length>0){
        K=U;
      }
    }else{
    K=[K];
  }
  if(K!==null){
    K.sort(function(c,Z){
      return YAHOO.util.Sort.compare(c,Z);
    });
    this._destroyTheadEl();
    var V=this._oColumnSet.getDefinitions();
    var I=V.splice(Y,1)[0];
    V.splice(Q,0,I);
    this._initColumnSet(V);
    this._initTheadEl();
    var M=this._oColumnSet.tree[0][Q];
    var X=M.getKeyIndex();
    if(X===null){
      U=[];
      J=this._oColumnSet.getDescendants(M);
      for(O=0,R=J.length;O<R;O++){
        S=J[O].getKeyIndex();
        if(S!==null){
          U[U.length]=S;
        }
      }
      if(U.length>0){
      X=U;
    }
  }else{
  X=[X];
}
var W=X.sort(function(c,Z){
  return YAHOO.util.Sort.compare(c,Z);
})[0];
this._reorderColgroupColEl(K,W);
var T=this._elTbody.rows;
if(T.length>0){
  var N=this.get("renderLoopSize"),L=T.length;
  this._oChainRender.add({
    method:function(c){
      if((this instanceof D)&&this._sId){
        var b=c.nCurrentRow,a,e,d,Z=N>0?Math.min(b+N,T.length):T.length,g=c.aIndexes,f;
        for(;b<Z;++b){
          e=[];
          f=T[b];
          for(a=g.length-1;a>-1;a--){
            e.push(f.removeChild(f.childNodes[g[a]]));
          }
          d=f.childNodes[W]||null;
          for(a=e.length-1;a>-1;a--){
            f.insertBefore(e[a],d);
          }
          }
          c.nCurrentRow=b;
    }
  },
  iterations:(N>0)?Math.ceil(L/N):1,
  argument:{
    nCurrentRow:0,
    aIndexes:K
  },
  scope:this,
  timeout:(N>0)?0:-1
  });
this._runRenderChain();
}
this.fireEvent("columnReorderEvent",{
  column:M
});
return M;
}
}
}
},
selectColumn:function(K){
  K=this.getColumn(K);
  if(K&&!K.selected){
    if(K.getKeyIndex()!==null){
      K.selected=true;
      var L=K.getThEl();
      C.addClass(L,D.CLASS_SELECTED);
      var J=this.getTbodyEl().rows;
      var I=this._oChainRender;
      I.add({
        method:function(M){
          if((this instanceof D)&&this._sId&&J[M.rowIndex]&&J[M.rowIndex].cells[M.cellIndex]){
            C.addClass(J[M.rowIndex].cells[M.cellIndex],D.CLASS_SELECTED);
          }
          M.rowIndex++;
        },
        scope:this,
        iterations:J.length,
        argument:{
          rowIndex:0,
          cellIndex:K.getKeyIndex()
          }
        });
    this._clearTrTemplateEl();
    this._elTbody.style.display="none";
    this._runRenderChain();
    this._elTbody.style.display="";
    this.fireEvent("columnSelectEvent",{
      column:K
    });
  }else{}
}
},
unselectColumn:function(K){
  K=this.getColumn(K);
  if(K&&K.selected){
    if(K.getKeyIndex()!==null){
      K.selected=false;
      var L=K.getThEl();
      C.removeClass(L,D.CLASS_SELECTED);
      var J=this.getTbodyEl().rows;
      var I=this._oChainRender;
      I.add({
        method:function(M){
          if((this instanceof D)&&this._sId&&J[M.rowIndex]&&J[M.rowIndex].cells[M.cellIndex]){
            C.removeClass(J[M.rowIndex].cells[M.cellIndex],D.CLASS_SELECTED);
          }
          M.rowIndex++;
        },
        scope:this,
        iterations:J.length,
        argument:{
          rowIndex:0,
          cellIndex:K.getKeyIndex()
          }
        });
    this._clearTrTemplateEl();
    this._elTbody.style.display="none";
    this._runRenderChain();
    this._elTbody.style.display="";
    this.fireEvent("columnUnselectEvent",{
      column:K
    });
  }else{}
}
},
getSelectedColumns:function(M){
  var J=[];
  var K=this._oColumnSet.keys;
  for(var L=0,I=K.length;L<I;L++){
    if(K[L].selected){
      J[J.length]=K[L];
    }
  }
  return J;
},
highlightColumn:function(I){
  var L=this.getColumn(I);
  if(L&&(L.getKeyIndex()!==null)){
    var M=L.getThEl();
    C.addClass(M,D.CLASS_HIGHLIGHTED);
    var K=this.getTbodyEl().rows;
    var J=this._oChainRender;
    J.add({
      method:function(N){
        if((this instanceof D)&&this._sId&&K[N.rowIndex]&&K[N.rowIndex].cells[N.cellIndex]){
          C.addClass(K[N.rowIndex].cells[N.cellIndex],D.CLASS_HIGHLIGHTED);
        }
        N.rowIndex++;
      },
      scope:this,
      iterations:K.length,
      argument:{
        rowIndex:0,
        cellIndex:L.getKeyIndex()
        },
      timeout:-1
    });
    this._elTbody.style.display="none";
    this._runRenderChain();
    this._elTbody.style.display="";
    this.fireEvent("columnHighlightEvent",{
      column:L
    });
  }else{}
},
unhighlightColumn:function(I){
  var L=this.getColumn(I);
  if(L&&(L.getKeyIndex()!==null)){
    var M=L.getThEl();
    C.removeClass(M,D.CLASS_HIGHLIGHTED);
    var K=this.getTbodyEl().rows;
    var J=this._oChainRender;
    J.add({
      method:function(N){
        if((this instanceof D)&&this._sId&&K[N.rowIndex]&&K[N.rowIndex].cells[N.cellIndex]){
          C.removeClass(K[N.rowIndex].cells[N.cellIndex],D.CLASS_HIGHLIGHTED);
        }
        N.rowIndex++;
      },
      scope:this,
      iterations:K.length,
      argument:{
        rowIndex:0,
        cellIndex:L.getKeyIndex()
        },
      timeout:-1
    });
    this._elTbody.style.display="none";
    this._runRenderChain();
    this._elTbody.style.display="";
    this.fireEvent("columnUnhighlightEvent",{
      column:L
    });
  }else{}
},
addRow:function(O,K){
  if(H.isNumber(K)&&(K<0||K>this._oRecordSet.getLength())){
    return;
  }
  if(O&&H.isObject(O)){
    var M=this._oRecordSet.addRecord(O,K);
    if(M){
      var I;
      var J=this.get("paginator");
      if(J){
        var N=J.get("totalRecords");
        if(N!==E.Paginator.VALUE_UNLIMITED){
          J.set("totalRecords",N+1);
        }
        I=this.getRecordIndex(M);
        var L=(J.getPageRecords())[1];
        if(I<=L){
          this.render();
        }
        this.fireEvent("rowAddEvent",{
          record:M
        });
        return;
      }else{
        I=this.getTrIndex(M);
        if(H.isNumber(I)){
          this._oChainRender.add({
            method:function(R){
              if((this instanceof D)&&this._sId){
                var S=R.record;
                var P=R.recIndex;
                var T=this._addTrEl(S);
                if(T){
                  var Q=(this._elTbody.rows[P])?this._elTbody.rows[P]:null;
                  this._elTbody.insertBefore(T,Q);
                  if(P===0){
                    this._setFirstRow();
                  }
                  if(Q===null){
                    this._setLastRow();
                  }
                  this._setRowStripes();
                  this.hideTableMessage();
                  this.fireEvent("rowAddEvent",{
                    record:S
                  });
                }
              }
            },
          argument:{
            record:M,
            recIndex:I
          },
          scope:this,
          timeout:(this.get("renderLoopSize")>0)?0:-1
          });
      this._runRenderChain();
      return;
    }
  }
}
}
},
addRows:function(K,N){
  if(H.isNumber(N)&&(N<0||N>this._oRecordSet.getLength())){
    return;
  }
  if(H.isArray(K)){
    var O=this._oRecordSet.addRecords(K,N);
    if(O){
      var S=this.getRecordIndex(O[0]);
      var R=this.get("paginator");
      if(R){
        var P=R.get("totalRecords");
        if(P!==E.Paginator.VALUE_UNLIMITED){
          R.set("totalRecords",P+O.length);
        }
        var Q=(R.getPageRecords())[1];
        if(S<=Q){
          this.render();
        }
        this.fireEvent("rowsAddEvent",{
          records:O
        });
        return;
      }else{
        var M=this.get("renderLoopSize");
        var J=S+K.length;
        var I=(J-S);
        var L=(S>=this._elTbody.rows.length);
        this._oChainRender.add({
          method:function(X){
            if((this instanceof D)&&this._sId){
              var Y=X.aRecords,W=X.nCurrentRow,V=X.nCurrentRecord,T=M>0?Math.min(W+M,J):J,Z=document.createDocumentFragment(),U=(this._elTbody.rows[W])?this._elTbody.rows[W]:null;
              for(;W<T;W++,V++){
                Z.appendChild(this._addTrEl(Y[V]));
              }
              this._elTbody.insertBefore(Z,U);
              X.nCurrentRow=W;
              X.nCurrentRecord=V;
            }
          },
        iterations:(M>0)?Math.ceil(J/M):1,
          argument:{
          nCurrentRow:S,
          nCurrentRecord:0,
          aRecords:O
        },
        scope:this,
        timeout:(M>0)?0:-1
          });
      this._oChainRender.add({
        method:function(U){
          var T=U.recIndex;
          if(T===0){
            this._setFirstRow();
          }
          if(U.isLast){
            this._setLastRow();
          }
          this._setRowStripes();
          this.fireEvent("rowsAddEvent",{
            records:O
          });
        },
        argument:{
          recIndex:S,
          isLast:L
        },
        scope:this,
        timeout:-1
      });
      this._runRenderChain();
      this.hideTableMessage();
      return;
    }
  }
}
},
updateRow:function(T,J){
  var Q=T;
  if(!H.isNumber(Q)){
    Q=this.getRecordIndex(T);
  }
  if(H.isNumber(Q)&&(Q>=0)){
    var R=this._oRecordSet,P=R.getRecord(Q);
    if(P){
      var N=this._oRecordSet.setRecord(J,Q),I=this.getTrEl(P),O=P?P.getData():null;
      if(N){
        var S=this._aSelections||[],M=0,K=P.getId(),L=N.getId();
        for(;M<S.length;M++){
          if((S[M]===K)){
            S[M]=L;
          }else{
            if(S[M].recordId===K){
              S[M].recordId=L;
            }
          }
        }
        this._oChainRender.add({
      method:function(){
        if((this instanceof D)&&this._sId){
          var V=this.get("paginator");
          if(V){
            var U=(V.getPageRecords())[0],W=(V.getPageRecords())[1];
            if((Q>=U)||(Q<=W)){
              this.render();
            }
          }else{
          if(I){
            this._updateTrEl(I,N);
          }else{
            this.getTbodyEl().appendChild(this._addTrEl(N));
          }
        }
        this.fireEvent("rowUpdateEvent",{
        record:N,
        oldData:O
      });
    }
    },
  scope:this,
  timeout:(this.get("renderLoopSize")>0)?0:-1
  });
this._runRenderChain();
return;
}
}
}
return;
},
updateRows:function(V,K){
  if(H.isArray(K)){
    var O=V,J=this._oRecordSet;
    if(!H.isNumber(V)){
      O=this.getRecordIndex(V);
    }
    if(H.isNumber(O)&&(O>=0)&&(O<J.getLength())){
      var Z=O+K.length,W=J.getRecords(O,K.length),b=J.setRecords(K,O);
      if(b){
        var Q=this._aSelections||[],Y=0,X,T,U;
        for(;Y<Q.length;Y++){
          for(X=0;X<W.length;X++){
            U=W[X].getId();
            if((Q[Y]===U)){
              Q[Y]=b[X].getId();
            }else{
              if(Q[Y].recordId===U){
                Q[Y].recordId=b[X].getId();
              }
            }
          }
        }
        var a=this.get("paginator");
  if(a){
    var P=(a.getPageRecords())[0],M=(a.getPageRecords())[1];
    if((O>=P)||(Z<=M)){
      this.render();
    }
    this.fireEvent("rowsAddEvent",{
      newRecords:b,
      oldRecords:W
    });
    return;
  }else{
    var I=this.get("renderLoopSize"),R=K.length,L=this._elTbody.rows.length,S=(Z>=L),N=(Z>L);
    this._oChainRender.add({
      method:function(f){
        if((this instanceof D)&&this._sId){
          var g=f.aRecords,e=f.nCurrentRow,d=f.nDataPointer,c=I>0?Math.min(e+I,O+g.length):O+g.length;
          for(;e<c;e++,d++){
            if(N&&(e>=L)){
              this._elTbody.appendChild(this._addTrEl(g[d]));
            }else{
              this._updateTrEl(this._elTbody.rows[e],g[d]);
            }
          }
          f.nCurrentRow=e;
        f.nDataPointer=d;
      }
    },
    iterations:(I>0)?Math.ceil(R/I):1,
    argument:{
      nCurrentRow:O,
      aRecords:b,
      nDataPointer:0,
      isAdding:N
    },
    scope:this,
    timeout:(I>0)?0:-1
    });
this._oChainRender.add({
  method:function(d){
    var c=d.recIndex;
    if(c===0){
      this._setFirstRow();
    }
    if(d.isLast){
      this._setLastRow();
    }
    this._setRowStripes();
    this.fireEvent("rowsAddEvent",{
      newRecords:b,
      oldRecords:W
    });
  },
  argument:{
    recIndex:O,
    isLast:S
  },
  scope:this,
  timeout:-1
});
this._runRenderChain();
this.hideTableMessage();
return;
}
}
}
}
},
deleteRow:function(R){
  var J=(H.isNumber(R))?R:this.getRecordIndex(R);
  if(H.isNumber(J)){
    var S=this.getRecord(J);
    if(S){
      var L=this.getTrIndex(J);
      var O=S.getId();
      var Q=this._aSelections||[];
      for(var M=Q.length-1;M>-1;M--){
        if((H.isString(Q[M])&&(Q[M]===O))||(H.isObject(Q[M])&&(Q[M].recordId===O))){
          Q.splice(M,1);
        }
      }
      var K=this._oRecordSet.deleteRecord(J);
    if(K){
      var P=this.get("paginator");
      if(P){
        var N=P.get("totalRecords"),I=P.getPageRecords();
        if(N!==E.Paginator.VALUE_UNLIMITED){
          P.set("totalRecords",N-1);
        }
        if(!I||J<=I[1]){
          this.render();
        }
        this._oChainRender.add({
          method:function(){
            if((this instanceof D)&&this._sId){
              this.fireEvent("rowDeleteEvent",{
                recordIndex:J,
                oldData:K,
                trElIndex:L
              });
            }
          },
        scope:this,
        timeout:(this.get("renderLoopSize")>0)?0:-1
          });
      this._runRenderChain();
    }else{
      if(H.isNumber(L)){
        this._oChainRender.add({
          method:function(){
            if((this instanceof D)&&this._sId){
              var T=(J===this._oRecordSet.getLength());
              this._deleteTrEl(L);
              if(this._elTbody.rows.length>0){
                if(L===0){
                  this._setFirstRow();
                }
                if(T){
                  this._setLastRow();
                }
                if(L!=this._elTbody.rows.length){
                  this._setRowStripes(L);
                }
              }
              this.fireEvent("rowDeleteEvent",{
              recordIndex:J,
              oldData:K,
              trElIndex:L
            });
          }
        },
        scope:this,
        timeout:(this.get("renderLoopSize")>0)?0:-1
        });
    this._runRenderChain();
    return;
  }
}
}
}
}
return null;
},
deleteRows:function(X,R){
  var K=(H.isNumber(X))?X:this.getRecordIndex(X);
  if(H.isNumber(K)){
    var Y=this.getRecord(K);
    if(Y){
      var L=this.getTrIndex(K);
      var T=Y.getId();
      var W=this._aSelections||[];
      for(var P=W.length-1;P>-1;P--){
        if((H.isString(W[P])&&(W[P]===T))||(H.isObject(W[P])&&(W[P].recordId===T))){
          W.splice(P,1);
        }
      }
      var M=K;
    var V=K;
    if(R&&H.isNumber(R)){
      M=(R>0)?K+R-1:K;
      V=(R>0)?K:K+R+1;
      R=(R>0)?R:R*-1;
      if(V<0){
        V=0;
        R=M-V+1;
      }
    }else{
    R=1;
  }
  var O=this._oRecordSet.deleteRecords(V,R);
  if(O){
    var U=this.get("paginator"),Q=this.get("renderLoopSize");
    if(U){
      var S=U.get("totalRecords"),J=U.getPageRecords();
      if(S!==E.Paginator.VALUE_UNLIMITED){
        U.set("totalRecords",S-O.length);
      }
      if(!J||V<=J[1]){
        this.render();
      }
      this._oChainRender.add({
        method:function(Z){
          if((this instanceof D)&&this._sId){
            this.fireEvent("rowsDeleteEvent",{
              recordIndex:V,
              oldData:O,
              count:R
            });
          }
        },
      scope:this,
      timeout:(Q>0)?0:-1
        });
    this._runRenderChain();
    return;
  }else{
    if(H.isNumber(L)){
      var N=V;
      var I=R;
      this._oChainRender.add({
        method:function(b){
          if((this instanceof D)&&this._sId){
            var a=b.nCurrentRow,Z=(Q>0)?(Math.max(a-Q,N)-1):N-1;
            for(;a>Z;--a){
              this._deleteTrEl(a);
            }
            b.nCurrentRow=a;
          }
        },
      iterations:(Q>0)?Math.ceil(R/Q):1,
        argument:{
        nCurrentRow:M
      },
      scope:this,
      timeout:(Q>0)?0:-1
        });
    this._oChainRender.add({
      method:function(){
        if(this._elTbody.rows.length>0){
          this._setFirstRow();
          this._setLastRow();
          this._setRowStripes();
        }
        this.fireEvent("rowsDeleteEvent",{
          recordIndex:V,
          oldData:O,
          count:R
        });
      },
      scope:this,
      timeout:-1
    });
    this._runRenderChain();
    return;
  }
}
}
}
}
return null;
},
formatCell:function(J,L,M){
  if(!L){
    L=this.getRecord(J);
  }
  if(!M){
    M=this.getColumn(J.parentNode.cellIndex);
  }
  if(L&&M){
    var I=M.field;
    var N=L.getData(I);
    var K=typeof M.formatter==="function"?M.formatter:D.Formatter[M.formatter+""]||D.Formatter.defaultFormatter;
    if(K){
      K.call(this,J,L,M,N);
    }else{
      J.innerHTML=N;
    }
    this.fireEvent("cellFormatEvent",{
      record:L,
      column:M,
      key:M.key,
      el:J
    });
  }else{}
},
updateCell:function(J,L,N){
  L=(L instanceof YAHOO.widget.Column)?L:this.getColumn(L);
  if(L&&L.getField()&&(J instanceof YAHOO.widget.Record)){
    var K=L.getField(),M=J.getData(K);
    this._oRecordSet.updateRecordValue(J,K,N);
    var I=this.getTdEl({
      record:J,
      column:L
    });
    if(I){
      this._oChainRender.add({
        method:function(){
          if((this instanceof D)&&this._sId){
            this.formatCell(I.firstChild);
            this.fireEvent("cellUpdateEvent",{
              record:J,
              column:L,
              oldData:M
            });
          }
        },
      scope:this,
      timeout:(this.get("renderLoopSize")>0)?0:-1
        });
    this._runRenderChain();
  }else{
    this.fireEvent("cellUpdateEvent",{
      record:J,
      column:L,
      oldData:M
    });
  }
}
},
_updatePaginator:function(J){
  var I=this.get("paginator");
  if(I&&J!==I){
    I.unsubscribe("changeRequest",this.onPaginatorChangeRequest,this,true);
  }
  if(J){
    J.subscribe("changeRequest",this.onPaginatorChangeRequest,this,true);
  }
},
_handlePaginatorChange:function(K){
  if(K.prevValue===K.newValue){
    return;
  }
  var M=K.newValue,L=K.prevValue,J=this._defaultPaginatorContainers();
  if(L){
    if(L.getContainerNodes()[0]==J[0]){
      L.set("containers",[]);
    }
    L.destroy();
    if(J[0]){
      if(M&&!M.getContainerNodes().length){
        M.set("containers",J);
      }else{
        for(var I=J.length-1;I>=0;--I){
          if(J[I]){
            J[I].parentNode.removeChild(J[I]);
          }
        }
        }
    }
}
if(!this._bInit){
  this.render();
}
if(M){
  this.renderPaginator();
}
},
_defaultPaginatorContainers:function(L){
  var J=this._sId+"-paginator0",K=this._sId+"-paginator1",I=C.get(J),M=C.get(K);
  if(L&&(!I||!M)){
    if(!I){
      I=document.createElement("div");
      I.id=J;
      C.addClass(I,D.CLASS_PAGINATOR);
      this._elContainer.insertBefore(I,this._elContainer.firstChild);
    }
    if(!M){
      M=document.createElement("div");
      M.id=K;
      C.addClass(M,D.CLASS_PAGINATOR);
      this._elContainer.appendChild(M);
    }
  }
  return[I,M];
},
_destroyPaginator:function(){
  var I=this.get("paginator");
  if(I){
    I.destroy();
  }
},
renderPaginator:function(){
  var I=this.get("paginator");
  if(!I){
    return;
  }
  if(!I.getContainerNodes().length){
    I.set("containers",this._defaultPaginatorContainers(true));
  }
  I.render();
},
doBeforePaginatorChange:function(I){
  this.showTableMessage(this.get("MSG_LOADING"),D.CLASS_LOADING);
  return true;
},
onPaginatorChangeRequest:function(L){
  var J=this.doBeforePaginatorChange(L);
  if(J){
    if(this.get("dynamicData")){
      var I=this.getState();
      I.pagination=L;
      var K=this.get("generateRequest")(I,this);
      this.unselectAllRows();
      this.unselectAllCells();
      var M={
        success:this.onDataReturnSetRows,
        failure:this.onDataReturnSetRows,
        argument:I,
        scope:this
      };

      this._oDataSource.sendRequest(K,M);
    }else{
      L.paginator.setStartIndex(L.recordOffset,true);
      L.paginator.setRowsPerPage(L.rowsPerPage,true);
      this.render();
    }
  }else{}
},
_elLastHighlightedTd:null,
_aSelections:null,
_oAnchorRecord:null,
_oAnchorCell:null,
_unselectAllTrEls:function(){
  var I=C.getElementsByClassName(D.CLASS_SELECTED,"tr",this._elTbody);
  C.removeClass(I,D.CLASS_SELECTED);
},
_getSelectionTrigger:function(){
  var L=this.get("selectionMode");
  var K={};

  var O,I,J,N,M;
  if((L=="cellblock")||(L=="cellrange")||(L=="singlecell")){
    O=this.getLastSelectedCell();
    if(!O){
      return null;
    }else{
      I=this.getRecord(O.recordId);
      J=this.getRecordIndex(I);
      N=this.getTrEl(I);
      M=this.getTrIndex(N);
      if(M===null){
        return null;
      }else{
        K.record=I;
        K.recordIndex=J;
        K.el=this.getTdEl(O);
        K.trIndex=M;
        K.column=this.getColumn(O.columnKey);
        K.colKeyIndex=K.column.getKeyIndex();
        K.cell=O;
        return K;
      }
    }
  }else{
  I=this.getLastSelectedRecord();
  if(!I){
    return null;
  }else{
    I=this.getRecord(I);
    J=this.getRecordIndex(I);
    N=this.getTrEl(I);
    M=this.getTrIndex(N);
    if(M===null){
      return null;
    }else{
      K.record=I;
      K.recordIndex=J;
      K.el=N;
      K.trIndex=M;
      return K;
    }
  }
}
},
_getSelectionAnchor:function(K){
  var J=this.get("selectionMode");
  var L={};

  var M,O,I;
  if((J=="cellblock")||(J=="cellrange")||(J=="singlecell")){
    var N=this._oAnchorCell;
    if(!N){
      if(K){
        N=this._oAnchorCell=K.cell;
      }else{
        return null;
      }
    }
    M=this._oAnchorCell.record;
  O=this._oRecordSet.getRecordIndex(M);
  I=this.getTrIndex(M);
  if(I===null){
    if(O<this.getRecordIndex(this.getFirstTrEl())){
      I=0;
    }else{
      I=this.getRecordIndex(this.getLastTrEl());
    }
  }
  L.record=M;
L.recordIndex=O;
L.trIndex=I;
L.column=this._oAnchorCell.column;
L.colKeyIndex=L.column.getKeyIndex();
L.cell=N;
return L;
}else{
  M=this._oAnchorRecord;
  if(!M){
    if(K){
      M=this._oAnchorRecord=K.record;
    }else{
      return null;
    }
  }
  O=this.getRecordIndex(M);
I=this.getTrIndex(M);
if(I===null){
  if(O<this.getRecordIndex(this.getFirstTrEl())){
    I=0;
  }else{
    I=this.getRecordIndex(this.getLastTrEl());
  }
}
L.record=M;
L.recordIndex=O;
L.trIndex=I;
return L;
}
},
_handleStandardSelectionByMouse:function(J){
  var I=J.target;
  var L=this.getTrEl(I);
  if(L){
    var O=J.event;
    var R=O.shiftKey;
    var N=O.ctrlKey||((navigator.userAgent.toLowerCase().indexOf("mac")!=-1)&&O.metaKey);
    var Q=this.getRecord(L);
    var K=this._oRecordSet.getRecordIndex(Q);
    var P=this._getSelectionAnchor();
    var M;
    if(R&&N){
      if(P){
        if(this.isSelected(P.record)){
          if(P.recordIndex<K){
            for(M=P.recordIndex+1;M<=K;M++){
              if(!this.isSelected(M)){
                this.selectRow(M);
              }
            }
            }else{
        for(M=P.recordIndex-1;M>=K;M--){
          if(!this.isSelected(M)){
            this.selectRow(M);
          }
        }
        }
    }else{
  if(P.recordIndex<K){
    for(M=P.recordIndex+1;M<=K-1;M++){
      if(this.isSelected(M)){
        this.unselectRow(M);
      }
    }
    }else{
  for(M=K+1;M<=P.recordIndex-1;M++){
    if(this.isSelected(M)){
      this.unselectRow(M);
    }
  }
  }
this.selectRow(Q);
}
}else{
  this._oAnchorRecord=Q;
  if(this.isSelected(Q)){
    this.unselectRow(Q);
  }else{
    this.selectRow(Q);
  }
}
}else{
  if(R){
    this.unselectAllRows();
    if(P){
      if(P.recordIndex<K){
        for(M=P.recordIndex;M<=K;M++){
          this.selectRow(M);
        }
        }else{
      for(M=P.recordIndex;M>=K;M--){
        this.selectRow(M);
      }
      }
    }else{
  this._oAnchorRecord=Q;
  this.selectRow(Q);
}
}else{
  if(N){
    this._oAnchorRecord=Q;
    if(this.isSelected(Q)){
      this.unselectRow(Q);
    }else{
      this.selectRow(Q);
    }
  }else{
  this._handleSingleSelectionByMouse(J);
  return;
}
}
}
}
},
_handleStandardSelectionByKey:function(M){
  var I=G.getCharCode(M);
  if((I==38)||(I==40)){
    var K=M.shiftKey;
    var J=this._getSelectionTrigger();
    if(!J){
      return null;
    }
    G.stopEvent(M);
    var L=this._getSelectionAnchor(J);
    if(K){
      if((I==40)&&(L.recordIndex<=J.trIndex)){
        this.selectRow(this.getNextTrEl(J.el));
      }else{
        if((I==38)&&(L.recordIndex>=J.trIndex)){
          this.selectRow(this.getPreviousTrEl(J.el));
        }else{
          this.unselectRow(J.el);
        }
      }
    }else{
  this._handleSingleSelectionByKey(M);
}
}
},
_handleSingleSelectionByMouse:function(K){
  var L=K.target;
  var J=this.getTrEl(L);
  if(J){
    var I=this.getRecord(J);
    this._oAnchorRecord=I;
    this.unselectAllRows();
    this.selectRow(I);
  }
},
_handleSingleSelectionByKey:function(L){
  var I=G.getCharCode(L);
  if((I==38)||(I==40)){
    var J=this._getSelectionTrigger();
    if(!J){
      return null;
    }
    G.stopEvent(L);
    var K;
    if(I==38){
      K=this.getPreviousTrEl(J.el);
      if(K===null){
        K=this.getFirstTrEl();
      }
    }else{
    if(I==40){
      K=this.getNextTrEl(J.el);
      if(K===null){
        K=this.getLastTrEl();
      }
    }
  }
this.unselectAllRows();
this.selectRow(K);
this._oAnchorRecord=this.getRecord(K);
}
},
_handleCellBlockSelectionByMouse:function(Y){
  var Z=Y.target;
  var J=this.getTdEl(Z);
  if(J){
    var X=Y.event;
    var O=X.shiftKey;
    var K=X.ctrlKey||((navigator.userAgent.toLowerCase().indexOf("mac")!=-1)&&X.metaKey);
    var Q=this.getTrEl(J);
    var P=this.getTrIndex(Q);
    var T=this.getColumn(J);
    var U=T.getKeyIndex();
    var S=this.getRecord(Q);
    var b=this._oRecordSet.getRecordIndex(S);
    var N={
      record:S,
      column:T
    };

    var R=this._getSelectionAnchor();
    var M=this.getTbodyEl().rows;
    var L,I,a,W,V;
    if(O&&K){
      if(R){
        if(this.isSelected(R.cell)){
          if(R.recordIndex===b){
            if(R.colKeyIndex<U){
              for(W=R.colKeyIndex+1;W<=U;W++){
                this.selectCell(Q.cells[W]);
              }
              }else{
            if(U<R.colKeyIndex){
              for(W=U;W<R.colKeyIndex;W++){
                this.selectCell(Q.cells[W]);
              }
              }
            }
      }else{
    if(R.recordIndex<b){
      L=Math.min(R.colKeyIndex,U);
      I=Math.max(R.colKeyIndex,U);
      for(W=R.trIndex;W<=P;W++){
        for(V=L;V<=I;V++){
          this.selectCell(M[W].cells[V]);
        }
        }
      }else{
  L=Math.min(R.trIndex,U);
  I=Math.max(R.trIndex,U);
  for(W=R.trIndex;W>=P;W--){
    for(V=I;V>=L;V--){
      this.selectCell(M[W].cells[V]);
    }
    }
  }
}
}else{
  if(R.recordIndex===b){
    if(R.colKeyIndex<U){
      for(W=R.colKeyIndex+1;W<U;W++){
        this.unselectCell(Q.cells[W]);
      }
      }else{
    if(U<R.colKeyIndex){
      for(W=U+1;W<R.colKeyIndex;W++){
        this.unselectCell(Q.cells[W]);
      }
      }
    }
}
if(R.recordIndex<b){
  for(W=R.trIndex;W<=P;W++){
    a=M[W];
    for(V=0;V<a.cells.length;V++){
      if(a.sectionRowIndex===R.trIndex){
        if(V>R.colKeyIndex){
          this.unselectCell(a.cells[V]);
        }
      }else{
      if(a.sectionRowIndex===P){
        if(V<U){
          this.unselectCell(a.cells[V]);
        }
      }else{
      this.unselectCell(a.cells[V]);
    }
    }
  }
}
}else{
  for(W=P;W<=R.trIndex;W++){
    a=M[W];
    for(V=0;V<a.cells.length;V++){
      if(a.sectionRowIndex==P){
        if(V>U){
          this.unselectCell(a.cells[V]);
        }
      }else{
      if(a.sectionRowIndex==R.trIndex){
        if(V<R.colKeyIndex){
          this.unselectCell(a.cells[V]);
        }
      }else{
      this.unselectCell(a.cells[V]);
    }
    }
  }
}
}
this.selectCell(J);
}
}else{
  this._oAnchorCell=N;
  if(this.isSelected(N)){
    this.unselectCell(N);
  }else{
    this.selectCell(N);
  }
}
}else{
  if(O){
    this.unselectAllCells();
    if(R){
      if(R.recordIndex===b){
        if(R.colKeyIndex<U){
          for(W=R.colKeyIndex;W<=U;W++){
            this.selectCell(Q.cells[W]);
          }
          }else{
        if(U<R.colKeyIndex){
          for(W=U;W<=R.colKeyIndex;W++){
            this.selectCell(Q.cells[W]);
          }
          }
        }
  }else{
  if(R.recordIndex<b){
    L=Math.min(R.colKeyIndex,U);
    I=Math.max(R.colKeyIndex,U);
    for(W=R.trIndex;W<=P;W++){
      for(V=L;V<=I;V++){
        this.selectCell(M[W].cells[V]);
      }
      }
    }else{
  L=Math.min(R.colKeyIndex,U);
  I=Math.max(R.colKeyIndex,U);
  for(W=P;W<=R.trIndex;W++){
    for(V=L;V<=I;V++){
      this.selectCell(M[W].cells[V]);
    }
    }
  }
}
}else{
  this._oAnchorCell=N;
  this.selectCell(N);
}
}else{
  if(K){
    this._oAnchorCell=N;
    if(this.isSelected(N)){
      this.unselectCell(N);
    }else{
      this.selectCell(N);
    }
  }else{
  this._handleSingleCellSelectionByMouse(Y);
}
}
}
}
},
_handleCellBlockSelectionByKey:function(N){
  var I=G.getCharCode(N);
  var S=N.shiftKey;
  if((I==9)||!S){
    this._handleSingleCellSelectionByKey(N);
    return;
  }
  if((I>36)&&(I<41)){
    var T=this._getSelectionTrigger();
    if(!T){
      return null;
    }
    G.stopEvent(N);
    var Q=this._getSelectionAnchor(T);
    var J,R,K,P,L;
    var O=this.getTbodyEl().rows;
    var M=T.el.parentNode;
    if(I==40){
      if(Q.recordIndex<=T.recordIndex){
        L=this.getNextTrEl(T.el);
        if(L){
          R=Q.colKeyIndex;
          K=T.colKeyIndex;
          if(R>K){
            for(J=R;J>=K;J--){
              P=L.cells[J];
              this.selectCell(P);
            }
            }else{
          for(J=R;J<=K;J++){
            P=L.cells[J];
            this.selectCell(P);
          }
          }
        }
  }else{
  R=Math.min(Q.colKeyIndex,T.colKeyIndex);
  K=Math.max(Q.colKeyIndex,T.colKeyIndex);
  for(J=R;J<=K;J++){
    this.unselectCell(M.cells[J]);
  }
  }
}else{
  if(I==38){
    if(Q.recordIndex>=T.recordIndex){
      L=this.getPreviousTrEl(T.el);
      if(L){
        R=Q.colKeyIndex;
        K=T.colKeyIndex;
        if(R>K){
          for(J=R;J>=K;J--){
            P=L.cells[J];
            this.selectCell(P);
          }
          }else{
        for(J=R;J<=K;J++){
          P=L.cells[J];
          this.selectCell(P);
        }
        }
      }
}else{
  R=Math.min(Q.colKeyIndex,T.colKeyIndex);
  K=Math.max(Q.colKeyIndex,T.colKeyIndex);
  for(J=R;J<=K;J++){
    this.unselectCell(M.cells[J]);
  }
  }
}else{
  if(I==39){
    if(Q.colKeyIndex<=T.colKeyIndex){
      if(T.colKeyIndex<M.cells.length-1){
        R=Q.trIndex;
        K=T.trIndex;
        if(R>K){
          for(J=R;J>=K;J--){
            P=O[J].cells[T.colKeyIndex+1];
            this.selectCell(P);
          }
          }else{
        for(J=R;J<=K;J++){
          P=O[J].cells[T.colKeyIndex+1];
          this.selectCell(P);
        }
        }
      }
}else{
  R=Math.min(Q.trIndex,T.trIndex);
  K=Math.max(Q.trIndex,T.trIndex);
  for(J=R;J<=K;J++){
    this.unselectCell(O[J].cells[T.colKeyIndex]);
  }
  }
}else{
  if(I==37){
    if(Q.colKeyIndex>=T.colKeyIndex){
      if(T.colKeyIndex>0){
        R=Q.trIndex;
        K=T.trIndex;
        if(R>K){
          for(J=R;J>=K;J--){
            P=O[J].cells[T.colKeyIndex-1];
            this.selectCell(P);
          }
          }else{
        for(J=R;J<=K;J++){
          P=O[J].cells[T.colKeyIndex-1];
          this.selectCell(P);
        }
        }
      }
}else{
  R=Math.min(Q.trIndex,T.trIndex);
  K=Math.max(Q.trIndex,T.trIndex);
  for(J=R;J<=K;J++){
    this.unselectCell(O[J].cells[T.colKeyIndex]);
  }
  }
}
}
}
}
}
},
_handleCellRangeSelectionByMouse:function(W){
  var X=W.target;
  var I=this.getTdEl(X);
  if(I){
    var V=W.event;
    var M=V.shiftKey;
    var J=V.ctrlKey||((navigator.userAgent.toLowerCase().indexOf("mac")!=-1)&&V.metaKey);
    var O=this.getTrEl(I);
    var N=this.getTrIndex(O);
    var R=this.getColumn(I);
    var S=R.getKeyIndex();
    var Q=this.getRecord(O);
    var Z=this._oRecordSet.getRecordIndex(Q);
    var L={
      record:Q,
      column:R
    };

    var P=this._getSelectionAnchor();
    var K=this.getTbodyEl().rows;
    var Y,U,T;
    if(M&&J){
      if(P){
        if(this.isSelected(P.cell)){
          if(P.recordIndex===Z){
            if(P.colKeyIndex<S){
              for(U=P.colKeyIndex+1;U<=S;U++){
                this.selectCell(O.cells[U]);
              }
              }else{
            if(S<P.colKeyIndex){
              for(U=S;U<P.colKeyIndex;U++){
                this.selectCell(O.cells[U]);
              }
              }
            }
      }else{
    if(P.recordIndex<Z){
      for(U=P.colKeyIndex+1;U<O.cells.length;U++){
        this.selectCell(O.cells[U]);
      }
      for(U=P.trIndex+1;U<N;U++){
        for(T=0;T<K[U].cells.length;T++){
          this.selectCell(K[U].cells[T]);
        }
        }
        for(U=0;U<=S;U++){
      this.selectCell(O.cells[U]);
    }
    }else{
  for(U=S;U<O.cells.length;U++){
    this.selectCell(O.cells[U]);
  }
  for(U=N+1;U<P.trIndex;U++){
    for(T=0;T<K[U].cells.length;T++){
      this.selectCell(K[U].cells[T]);
    }
    }
    for(U=0;U<P.colKeyIndex;U++){
  this.selectCell(O.cells[U]);
}
}
}
}else{
  if(P.recordIndex===Z){
    if(P.colKeyIndex<S){
      for(U=P.colKeyIndex+1;U<S;U++){
        this.unselectCell(O.cells[U]);
      }
      }else{
    if(S<P.colKeyIndex){
      for(U=S+1;U<P.colKeyIndex;U++){
        this.unselectCell(O.cells[U]);
      }
      }
    }
}
if(P.recordIndex<Z){
  for(U=P.trIndex;U<=N;U++){
    Y=K[U];
    for(T=0;T<Y.cells.length;T++){
      if(Y.sectionRowIndex===P.trIndex){
        if(T>P.colKeyIndex){
          this.unselectCell(Y.cells[T]);
        }
      }else{
      if(Y.sectionRowIndex===N){
        if(T<S){
          this.unselectCell(Y.cells[T]);
        }
      }else{
      this.unselectCell(Y.cells[T]);
    }
    }
  }
}
}else{
  for(U=N;U<=P.trIndex;U++){
    Y=K[U];
    for(T=0;T<Y.cells.length;T++){
      if(Y.sectionRowIndex==N){
        if(T>S){
          this.unselectCell(Y.cells[T]);
        }
      }else{
      if(Y.sectionRowIndex==P.trIndex){
        if(T<P.colKeyIndex){
          this.unselectCell(Y.cells[T]);
        }
      }else{
      this.unselectCell(Y.cells[T]);
    }
    }
  }
}
}
this.selectCell(I);
}
}else{
  this._oAnchorCell=L;
  if(this.isSelected(L)){
    this.unselectCell(L);
  }else{
    this.selectCell(L);
  }
}
}else{
  if(M){
    this.unselectAllCells();
    if(P){
      if(P.recordIndex===Z){
        if(P.colKeyIndex<S){
          for(U=P.colKeyIndex;U<=S;U++){
            this.selectCell(O.cells[U]);
          }
          }else{
        if(S<P.colKeyIndex){
          for(U=S;U<=P.colKeyIndex;U++){
            this.selectCell(O.cells[U]);
          }
          }
        }
  }else{
  if(P.recordIndex<Z){
    for(U=P.trIndex;U<=N;U++){
      Y=K[U];
      for(T=0;T<Y.cells.length;T++){
        if(Y.sectionRowIndex==P.trIndex){
          if(T>=P.colKeyIndex){
            this.selectCell(Y.cells[T]);
          }
        }else{
        if(Y.sectionRowIndex==N){
          if(T<=S){
            this.selectCell(Y.cells[T]);
          }
        }else{
        this.selectCell(Y.cells[T]);
      }
      }
    }
  }
}else{
  for(U=N;U<=P.trIndex;U++){
    Y=K[U];
    for(T=0;T<Y.cells.length;T++){
      if(Y.sectionRowIndex==N){
        if(T>=S){
          this.selectCell(Y.cells[T]);
        }
      }else{
      if(Y.sectionRowIndex==P.trIndex){
        if(T<=P.colKeyIndex){
          this.selectCell(Y.cells[T]);
        }
      }else{
      this.selectCell(Y.cells[T]);
    }
    }
  }
}
}
}
}else{
  this._oAnchorCell=L;
  this.selectCell(L);
}
}else{
  if(J){
    this._oAnchorCell=L;
    if(this.isSelected(L)){
      this.unselectCell(L);
    }else{
      this.selectCell(L);
    }
  }else{
  this._handleSingleCellSelectionByMouse(W);
}
}
}
}
},
_handleCellRangeSelectionByKey:function(M){
  var I=G.getCharCode(M);
  var Q=M.shiftKey;
  if((I==9)||!Q){
    this._handleSingleCellSelectionByKey(M);
    return;
  }
  if((I>36)&&(I<41)){
    var R=this._getSelectionTrigger();
    if(!R){
      return null;
    }
    G.stopEvent(M);
    var P=this._getSelectionAnchor(R);
    var J,K,O;
    var N=this.getTbodyEl().rows;
    var L=R.el.parentNode;
    if(I==40){
      K=this.getNextTrEl(R.el);
      if(P.recordIndex<=R.recordIndex){
        for(J=R.colKeyIndex+1;J<L.cells.length;J++){
          O=L.cells[J];
          this.selectCell(O);
        }
        if(K){
          for(J=0;J<=R.colKeyIndex;J++){
            O=K.cells[J];
            this.selectCell(O);
          }
          }
        }else{
    for(J=R.colKeyIndex;J<L.cells.length;J++){
      this.unselectCell(L.cells[J]);
    }
    if(K){
      for(J=0;J<R.colKeyIndex;J++){
        this.unselectCell(K.cells[J]);
      }
      }
    }
}else{
  if(I==38){
    K=this.getPreviousTrEl(R.el);
    if(P.recordIndex>=R.recordIndex){
      for(J=R.colKeyIndex-1;J>-1;J--){
        O=L.cells[J];
        this.selectCell(O);
      }
      if(K){
        for(J=L.cells.length-1;J>=R.colKeyIndex;J--){
          O=K.cells[J];
          this.selectCell(O);
        }
        }
      }else{
  for(J=R.colKeyIndex;J>-1;J--){
    this.unselectCell(L.cells[J]);
  }
  if(K){
    for(J=L.cells.length-1;J>R.colKeyIndex;
      J--){
      this.unselectCell(K.cells[J]);
    }
    }
  }
}else{
  if(I==39){
    K=this.getNextTrEl(R.el);
    if(P.recordIndex<R.recordIndex){
      if(R.colKeyIndex<L.cells.length-1){
        O=L.cells[R.colKeyIndex+1];
        this.selectCell(O);
      }else{
        if(K){
          O=K.cells[0];
          this.selectCell(O);
        }
      }
    }else{
  if(P.recordIndex>R.recordIndex){
    this.unselectCell(L.cells[R.colKeyIndex]);
    if(R.colKeyIndex<L.cells.length-1){}else{}
}else{
  if(P.colKeyIndex<=R.colKeyIndex){
    if(R.colKeyIndex<L.cells.length-1){
      O=L.cells[R.colKeyIndex+1];
      this.selectCell(O);
    }else{
      if(R.trIndex<N.length-1){
        O=K.cells[0];
        this.selectCell(O);
      }
    }
  }else{
  this.unselectCell(L.cells[R.colKeyIndex]);
}
}
}
}else{
  if(I==37){
    K=this.getPreviousTrEl(R.el);
    if(P.recordIndex<R.recordIndex){
      this.unselectCell(L.cells[R.colKeyIndex]);
      if(R.colKeyIndex>0){}else{}
  }else{
    if(P.recordIndex>R.recordIndex){
      if(R.colKeyIndex>0){
        O=L.cells[R.colKeyIndex-1];
        this.selectCell(O);
      }else{
        if(R.trIndex>0){
          O=K.cells[K.cells.length-1];
          this.selectCell(O);
        }
      }
    }else{
  if(P.colKeyIndex>=R.colKeyIndex){
    if(R.colKeyIndex>0){
      O=L.cells[R.colKeyIndex-1];
      this.selectCell(O);
    }else{
      if(R.trIndex>0){
        O=K.cells[K.cells.length-1];
        this.selectCell(O);
      }
    }
  }else{
  this.unselectCell(L.cells[R.colKeyIndex]);
  if(R.colKeyIndex>0){}else{}
}
}
}
}
}
}
}
}
},
_handleSingleCellSelectionByMouse:function(N){
  var O=N.target;
  var K=this.getTdEl(O);
  if(K){
    var J=this.getTrEl(K);
    var I=this.getRecord(J);
    var M=this.getColumn(K);
    var L={
      record:I,
      column:M
    };

    this._oAnchorCell=L;
    this.unselectAllCells();
    this.selectCell(L);
  }
},
_handleSingleCellSelectionByKey:function(M){
  var I=G.getCharCode(M);
  if((I==9)||((I>36)&&(I<41))){
    var K=M.shiftKey;
    var J=this._getSelectionTrigger();
    if(!J){
      return null;
    }
    var L;
    if(I==40){
      L=this.getBelowTdEl(J.el);
      if(L===null){
        L=J.el;
      }
    }else{
    if(I==38){
      L=this.getAboveTdEl(J.el);
      if(L===null){
        L=J.el;
      }
    }else{
    if((I==39)||(!K&&(I==9))){
      L=this.getNextTdEl(J.el);
      if(L===null){
        return;
      }
    }else{
    if((I==37)||(K&&(I==9))){
      L=this.getPreviousTdEl(J.el);
      if(L===null){
        return;
      }
    }
  }
}
}
G.stopEvent(M);
this.unselectAllCells();
this.selectCell(L);
this._oAnchorCell={
  record:this.getRecord(L),
  column:this.getColumn(L)
  };

}
},
getSelectedTrEls:function(){
  return C.getElementsByClassName(D.CLASS_SELECTED,"tr",this._elTbody);
},
selectRow:function(O){
  var N,I;
  if(O instanceof YAHOO.widget.Record){
    N=this._oRecordSet.getRecord(O);
    I=this.getTrEl(N);
  }else{
    if(H.isNumber(O)){
      N=this.getRecord(O);
      I=this.getTrEl(N);
    }else{
      I=this.getTrEl(O);
      N=this.getRecord(I);
    }
  }
  if(N){
  var M=this._aSelections||[];
  var L=N.getId();
  var K=-1;
  if(M.indexOf){
    K=M.indexOf(L);
  }else{
    for(var J=M.length-1;J>-1;J--){
      if(M[J]===L){
        K=J;
        break;
      }
    }
    }
  if(K>-1){
  M.splice(K,1);
}
M.push(L);
this._aSelections=M;
if(!this._oAnchorRecord){
  this._oAnchorRecord=N;
}
if(I){
  C.addClass(I,D.CLASS_SELECTED);
}
this.fireEvent("rowSelectEvent",{
  record:N,
  el:I
});
}else{}
},
unselectRow:function(O){
  var I=this.getTrEl(O);
  var N;
  if(O instanceof YAHOO.widget.Record){
    N=this._oRecordSet.getRecord(O);
  }else{
    if(H.isNumber(O)){
      N=this.getRecord(O);
    }else{
      N=this.getRecord(I);
    }
  }
  if(N){
  var M=this._aSelections||[];
  var L=N.getId();
  var K=-1;
  if(M.indexOf){
    K=M.indexOf(L);
  }else{
    for(var J=M.length-1;J>-1;J--){
      if(M[J]===L){
        K=J;
        break;
      }
    }
    }
  if(K>-1){
  M.splice(K,1);
  this._aSelections=M;
  C.removeClass(I,D.CLASS_SELECTED);
  this.fireEvent("rowUnselectEvent",{
    record:N,
    el:I
  });
  return;
}
}
},
unselectAllRows:function(){
  var J=this._aSelections||[],L,K=[];
  for(var I=J.length-1;I>-1;I--){
    if(H.isString(J[I])){
      L=J.splice(I,1);
      K[K.length]=this.getRecord(H.isArray(L)?L[0]:L);
    }
  }
  this._aSelections=J;
this._unselectAllTrEls();
this.fireEvent("unselectAllRowsEvent",{
  records:K
});
},
_unselectAllTdEls:function(){
  var I=C.getElementsByClassName(D.CLASS_SELECTED,"td",this._elTbody);
  C.removeClass(I,D.CLASS_SELECTED);
},
getSelectedTdEls:function(){
  return C.getElementsByClassName(D.CLASS_SELECTED,"td",this._elTbody);
},
selectCell:function(I){
  var O=this.getTdEl(I);
  if(O){
    var N=this.getRecord(O);
    var L=this.getColumn(O.cellIndex).getKey();
    if(N&&L){
      var M=this._aSelections||[];
      var K=N.getId();
      for(var J=M.length-1;J>-1;J--){
        if((M[J].recordId===K)&&(M[J].columnKey===L)){
          M.splice(J,1);
          break;
        }
      }
      M.push({
      recordId:K,
      columnKey:L
    });
    this._aSelections=M;
    if(!this._oAnchorCell){
      this._oAnchorCell={
        record:N,
        column:this.getColumn(L)
        };

  }
  C.addClass(O,D.CLASS_SELECTED);
  this.fireEvent("cellSelectEvent",{
    record:N,
    column:this.getColumn(O.cellIndex),
    key:this.getColumn(O.cellIndex).getKey(),
    el:O
  });
  return;
}
}
},
unselectCell:function(I){
  var N=this.getTdEl(I);
  if(N){
    var M=this.getRecord(N);
    var K=this.getColumn(N.cellIndex).getKey();
    if(M&&K){
      var L=this._aSelections||[];
      var O=M.getId();
      for(var J=L.length-1;J>-1;J--){
        if((L[J].recordId===O)&&(L[J].columnKey===K)){
          L.splice(J,1);
          this._aSelections=L;
          C.removeClass(N,D.CLASS_SELECTED);
          this.fireEvent("cellUnselectEvent",{
            record:M,
            column:this.getColumn(N.cellIndex),
            key:this.getColumn(N.cellIndex).getKey(),
            el:N
          });
          return;
        }
      }
      }
  }
},
unselectAllCells:function(){
  var J=this._aSelections||[];
  for(var I=J.length-1;I>-1;I--){
    if(H.isObject(J[I])){
      J.splice(I,1);
    }
  }
  this._aSelections=J;
this._unselectAllTdEls();
this.fireEvent("unselectAllCellsEvent");
},
isSelected:function(N){
  if(N&&(N.ownerDocument==document)){
    return(C.hasClass(this.getTdEl(N),D.CLASS_SELECTED)||C.hasClass(this.getTrEl(N),D.CLASS_SELECTED));
  }else{
    var M,J,I;
    var L=this._aSelections;
    if(L&&L.length>0){
      if(N instanceof YAHOO.widget.Record){
        M=N;
      }else{
        if(H.isNumber(N)){
          M=this.getRecord(N);
        }
      }
      if(M){
      J=M.getId();
      if(L.indexOf){
        if(L.indexOf(J)>-1){
          return true;
        }
      }else{
      for(I=L.length-1;I>-1;I--){
        if(L[I]===J){
          return true;
        }
      }
      }
  }else{
  if(N.record&&N.column){
    J=N.record.getId();
    var K=N.column.getKey();
    for(I=L.length-1;I>-1;I--){
      if((L[I].recordId===J)&&(L[I].columnKey===K)){
        return true;
      }
    }
    }
}
}
}
return false;
},
getSelectedRows:function(){
  var I=[];
  var K=this._aSelections||[];
  for(var J=0;J<K.length;J++){
    if(H.isString(K[J])){
      I.push(K[J]);
    }
  }
  return I;
},
getSelectedCells:function(){
  var J=[];
  var K=this._aSelections||[];
  for(var I=0;I<K.length;I++){
    if(K[I]&&H.isObject(K[I])){
      J.push(K[I]);
    }
  }
  return J;
},
getLastSelectedRecord:function(){
  var J=this._aSelections;
  if(J&&J.length>0){
    for(var I=J.length-1;I>-1;I--){
      if(H.isString(J[I])){
        return J[I];
      }
    }
    }
},
getLastSelectedCell:function(){
  var J=this._aSelections;
  if(J&&J.length>0){
    for(var I=J.length-1;I>-1;I--){
      if(J[I].recordId&&J[I].columnKey){
        return J[I];
      }
    }
    }
},
highlightRow:function(K){
  var I=this.getTrEl(K);
  if(I){
    var J=this.getRecord(I);
    C.addClass(I,D.CLASS_HIGHLIGHTED);
    this.fireEvent("rowHighlightEvent",{
      record:J,
      el:I
    });
    return;
  }
},
unhighlightRow:function(K){
  var I=this.getTrEl(K);
  if(I){
    var J=this.getRecord(I);
    C.removeClass(I,D.CLASS_HIGHLIGHTED);
    this.fireEvent("rowUnhighlightEvent",{
      record:J,
      el:I
    });
    return;
  }
},
highlightCell:function(I){
  var L=this.getTdEl(I);
  if(L){
    if(this._elLastHighlightedTd){
      this.unhighlightCell(this._elLastHighlightedTd);
    }
    var K=this.getRecord(L);
    var J=this.getColumn(L.cellIndex).getKey();
    C.addClass(L,D.CLASS_HIGHLIGHTED);
    this._elLastHighlightedTd=L;
    this.fireEvent("cellHighlightEvent",{
      record:K,
      column:this.getColumn(L.cellIndex),
      key:this.getColumn(L.cellIndex).getKey(),
      el:L
    });
    return;
  }
},
unhighlightCell:function(I){
  var K=this.getTdEl(I);
  if(K){
    var J=this.getRecord(K);
    C.removeClass(K,D.CLASS_HIGHLIGHTED);
    this._elLastHighlightedTd=null;
    this.fireEvent("cellUnhighlightEvent",{
      record:J,
      column:this.getColumn(K.cellIndex),
      key:this.getColumn(K.cellIndex).getKey(),
      el:K
    });
    return;
  }
},
getCellEditor:function(){
  return this._oCellEditor;
},
showCellEditor:function(P,Q,L){
  P=this.getTdEl(P);
  if(P){
    L=this.getColumn(P);
    if(L&&L.editor){
      var J=this._oCellEditor;
      if(J){
        if(this._oCellEditor.cancel){
          this._oCellEditor.cancel();
        }else{
          if(J.isActive){
            this.cancelCellEditor();
          }
        }
      }
    if(L.editor instanceof YAHOO.widget.BaseCellEditor){
    J=L.editor;
    var N=J.attach(this,P);
    if(N){
      J.move();
      N=this.doBeforeShowCellEditor(J);
      if(N){
        J.show();
        this._oCellEditor=J;
      }
    }
  }else{
  if(!Q||!(Q instanceof YAHOO.widget.Record)){
    Q=this.getRecord(P);
  }
  if(!L||!(L instanceof YAHOO.widget.Column)){
    L=this.getColumn(P);
  }
  if(Q&&L){
    if(!this._oCellEditor||this._oCellEditor.container){
      this._initCellEditorEl();
    }
    J=this._oCellEditor;
    J.cell=P;
    J.record=Q;
    J.column=L;
    J.validator=(L.editorOptions&&H.isFunction(L.editorOptions.validator))?L.editorOptions.validator:null;
    J.value=Q.getData(L.key);
    J.defaultValue=null;
    var K=J.container;
    var O=C.getX(P);
    var M=C.getY(P);
    if(isNaN(O)||isNaN(M)){
      O=P.offsetLeft+C.getX(this._elTbody.parentNode)-this._elTbody.scrollLeft;
      M=P.offsetTop+C.getY(this._elTbody.parentNode)-this._elTbody.scrollTop+this._elThead.offsetHeight;
    }
    K.style.left=O+"px";
    K.style.top=M+"px";
    this.doBeforeShowCellEditor(this._oCellEditor);
    K.style.display="";
    G.addListener(K,"keydown",function(S,R){
      if((S.keyCode==27)){
        R.cancelCellEditor();
        R.focusTbodyEl();
      }else{
        R.fireEvent("editorKeydownEvent",{
          editor:R._oCellEditor,
          event:S
        });
      }
    },this);
  var I;
  if(H.isString(L.editor)){
    switch(L.editor){
      case"checkbox":
        I=D.editCheckbox;
        break;
      case"date":
        I=D.editDate;
        break;
      case"dropdown":
        I=D.editDropdown;
        break;
      case"radio":
        I=D.editRadio;
        break;
      case"textarea":
        I=D.editTextarea;
        break;
      case"textbox":
        I=D.editTextbox;
        break;
      default:
        I=null;
    }
  }else{
  if(H.isFunction(L.editor)){
    I=L.editor;
  }
}
if(I){
  I(this._oCellEditor,this);
  if(!L.editorOptions||!L.editorOptions.disableBtns){
    this.showCellEditorBtns(K);
  }
  J.isActive=true;
  this.fireEvent("editorShowEvent",{
    editor:J
  });
  return;
}
}
}
}
}
},
_initCellEditorEl:function(){
  var I=document.createElement("div");
  I.id=this._sId+"-celleditor";
  I.style.display="none";
  I.tabIndex=0;
  C.addClass(I,D.CLASS_EDITOR);
  var K=C.getFirstChild(document.body);
  if(K){
    I=C.insertBefore(I,K);
  }else{
    I=document.body.appendChild(I);
  }
  var J={};

  J.container=I;
  J.value=null;
  J.isActive=false;
  this._oCellEditor=J;
},
doBeforeShowCellEditor:function(I){
  return true;
},
saveCellEditor:function(){
  if(this._oCellEditor){
    if(this._oCellEditor.save){
      this._oCellEditor.save();
    }else{
      if(this._oCellEditor.isActive){
        var I=this._oCellEditor.value;
        var J=this._oCellEditor.record.getData(this._oCellEditor.column.key);
        if(this._oCellEditor.validator){
          I=this._oCellEditor.value=this._oCellEditor.validator.call(this,I,J,this._oCellEditor);
          if(I===null){
            this.resetCellEditor();
            this.fireEvent("editorRevertEvent",{
              editor:this._oCellEditor,
              oldData:J,
              newData:I
            });
            return;
          }
        }
        this._oRecordSet.updateRecordValue(this._oCellEditor.record,this._oCellEditor.column.key,this._oCellEditor.value);
      this.formatCell(this._oCellEditor.cell.firstChild);
      this._oChainRender.add({
        method:function(){
          this.validateColumnWidths();
        },
        scope:this
      });
      this._oChainRender.run();
      this.resetCellEditor();
      this.fireEvent("editorSaveEvent",{
        editor:this._oCellEditor,
        oldData:J,
        newData:I
      });
    }
  }
}
},
cancelCellEditor:function(){
  if(this._oCellEditor){
    if(this._oCellEditor.cancel){
      this._oCellEditor.cancel();
    }else{
      if(this._oCellEditor.isActive){
        this.resetCellEditor();
        this.fireEvent("editorCancelEvent",{
          editor:this._oCellEditor
          });
      }
    }
  }
},
destroyCellEditor:function(){
  if(this._oCellEditor){
    this._oCellEditor.destroy();
    this._oCellEditor=null;
  }
},
_onEditorShowEvent:function(I){
  this.fireEvent("editorShowEvent",I);
},
_onEditorKeydownEvent:function(I){
  this.fireEvent("editorKeydownEvent",I);
},
_onEditorRevertEvent:function(I){
  this.fireEvent("editorRevertEvent",I);
},
_onEditorSaveEvent:function(I){
  this.fireEvent("editorSaveEvent",I);
},
_onEditorCancelEvent:function(I){
  this.fireEvent("editorCancelEvent",I);
},
_onEditorBlurEvent:function(I){
  this.fireEvent("editorBlurEvent",I);
},
_onEditorBlockEvent:function(I){
  this.fireEvent("editorBlockEvent",I);
},
_onEditorUnblockEvent:function(I){
  this.fireEvent("editorUnblockEvent",I);
},
onEditorBlurEvent:function(I){
  if(I.editor.disableBtns){
    if(I.editor.save){
      I.editor.save();
    }
  }else{
  if(I.editor.cancel){
    I.editor.cancel();
  }
}
},
onEditorBlockEvent:function(I){
  this.disable();
},
onEditorUnblockEvent:function(I){
  this.undisable();
},
doBeforeLoadData:function(I,J,K){
  return true;
},
onEventSortColumn:function(K){
  var I=K.event;
  var M=K.target;
  var J=this.getThEl(M)||this.getTdEl(M);
  if(J){
    var L=this.getColumn(J);
    if(L.sortable){
      G.stopEvent(I);
      this.sortColumn(L);
    }
  }else{}
},
onEventSelectColumn:function(I){
  this.selectColumn(I.target);
},
onEventHighlightColumn:function(I){
  if(!C.isAncestor(I.target,G.getRelatedTarget(I.event))){
    this.highlightColumn(I.target);
  }
},
onEventUnhighlightColumn:function(I){
  if(!C.isAncestor(I.target,G.getRelatedTarget(I.event))){
    this.unhighlightColumn(I.target);
  }
},
onEventSelectRow:function(J){
  var I=this.get("selectionMode");
  if(I=="single"){
    this._handleSingleSelectionByMouse(J);
  }else{
    this._handleStandardSelectionByMouse(J);
  }
},
onEventSelectCell:function(J){
  var I=this.get("selectionMode");
  if(I=="cellblock"){
    this._handleCellBlockSelectionByMouse(J);
  }else{
    if(I=="cellrange"){
      this._handleCellRangeSelectionByMouse(J);
    }else{
      this._handleSingleCellSelectionByMouse(J);
    }
  }
},
onEventHighlightRow:function(I){
  if(!C.isAncestor(I.target,G.getRelatedTarget(I.event))){
    this.highlightRow(I.target);
  }
},
onEventUnhighlightRow:function(I){
  if(!C.isAncestor(I.target,G.getRelatedTarget(I.event))){
    this.unhighlightRow(I.target);
  }
},
onEventHighlightCell:function(I){
  if(!C.isAncestor(I.target,G.getRelatedTarget(I.event))){
    this.highlightCell(I.target);
  }
},
onEventUnhighlightCell:function(I){
  if(!C.isAncestor(I.target,G.getRelatedTarget(I.event))){
    this.unhighlightCell(I.target);
  }
},
onEventFormatCell:function(I){
  var L=I.target;
  var J=this.getTdEl(L);
  if(J){
    var K=this.getColumn(J.cellIndex);
    this.formatCell(J.firstChild,this.getRecord(J),K);
  }else{}
},
onEventShowCellEditor:function(I){
  this.showCellEditor(I.target);
},
onEventSaveCellEditor:function(I){
  if(this._oCellEditor){
    if(this._oCellEditor.save){
      this._oCellEditor.save();
    }else{
      this.saveCellEditor();
    }
  }
},
onEventCancelCellEditor:function(I){
  if(this._oCellEditor){
    if(this._oCellEditor.cancel){
      this._oCellEditor.cancel();
    }else{
      this.cancelCellEditor();
    }
  }
},
onDataReturnInitializeTable:function(I,J,K){
  if((this instanceof D)&&this._sId){
    this.initializeTable();
    this.onDataReturnSetRows(I,J,K);
  }
},
onDataReturnReplaceRows:function(M,L,N){
  if((this instanceof D)&&this._sId){
    this.fireEvent("dataReturnEvent",{
      request:M,
      response:L,
      payload:N
    });
    var J=this.doBeforeLoadData(M,L,N),K=this.get("paginator"),I=0;
    if(J&&L&&!L.error&&H.isArray(L.results)){
      this._oRecordSet.reset();
      if(this.get("dynamicData")){
        if(N&&N.pagination&&H.isNumber(N.pagination.recordOffset)){
          I=N.pagination.recordOffset;
        }else{
          if(K){
            I=K.getStartIndex();
          }
        }
      }
    this._oRecordSet.setRecords(L.results,I|0);
  this._handleDataReturnPayload(M,L,N);
  this.render();
}else{
  if(J&&L.error){
    this.showTableMessage(this.get("MSG_ERROR"),D.CLASS_ERROR);
  }
}
}
},
onDataReturnAppendRows:function(J,K,L){
  if((this instanceof D)&&this._sId){
    this.fireEvent("dataReturnEvent",{
      request:J,
      response:K,
      payload:L
    });
    var I=this.doBeforeLoadData(J,K,L);
    if(I&&K&&!K.error&&H.isArray(K.results)){
      this.addRows(K.results);
      this._handleDataReturnPayload(J,K,L);
    }else{
      if(I&&K.error){
        this.showTableMessage(this.get("MSG_ERROR"),D.CLASS_ERROR);
      }
    }
  }
},
onDataReturnInsertRows:function(J,K,L){
  if((this instanceof D)&&this._sId){
    this.fireEvent("dataReturnEvent",{
      request:J,
      response:K,
      payload:L
    });
    var I=this.doBeforeLoadData(J,K,L);
    if(I&&K&&!K.error&&H.isArray(K.results)){
      this.addRows(K.results,(L?L.insertIndex:0));
      this._handleDataReturnPayload(J,K,L);
    }else{
      if(I&&K.error){
        this.showTableMessage(this.get("MSG_ERROR"),D.CLASS_ERROR);
      }
    }
  }
},
onDataReturnUpdateRows:function(J,K,L){
  if((this instanceof D)&&this._sId){
    this.fireEvent("dataReturnEvent",{
      request:J,
      response:K,
      payload:L
    });
    var I=this.doBeforeLoadData(J,K,L);
    if(I&&K&&!K.error&&H.isArray(K.results)){
      this.updateRows((L?L.updateIndex:0),K.results);
      this._handleDataReturnPayload(J,K,L);
    }else{
      if(I&&K.error){
        this.showTableMessage(this.get("MSG_ERROR"),D.CLASS_ERROR);
      }
    }
  }
},
onDataReturnSetRows:function(M,L,N){
  if((this instanceof D)&&this._sId){
    this.fireEvent("dataReturnEvent",{
      request:M,
      response:L,
      payload:N
    });
    var J=this.doBeforeLoadData(M,L,N),K=this.get("paginator"),I=0;
    if(J&&L&&!L.error&&H.isArray(L.results)){
      if(this.get("dynamicData")){
        if(N&&N.pagination&&H.isNumber(N.pagination.recordOffset)){
          I=N.pagination.recordOffset;
        }else{
          if(K){
            I=K.getStartIndex();
          }
        }
        this._oRecordSet.reset();
    }
    this._oRecordSet.setRecords(L.results,I|0);
    this._handleDataReturnPayload(M,L,N);
    this.render();
  }else{
    if(J&&L.error){
      this.showTableMessage(this.get("MSG_ERROR"),D.CLASS_ERROR);
    }
  }
}else{}
},
handleDataReturnPayload:function(J,I,K){
  return K;
},
_handleDataReturnPayload:function(K,J,L){
  L=this.handleDataReturnPayload(K,J,L);
  if(L){
    var I=this.get("paginator");
    if(I){
      if(this.get("dynamicData")){
        if(E.Paginator.isNumeric(L.totalRecords)){
          I.set("totalRecords",L.totalRecords);
        }
      }else{
      I.set("totalRecords",this._oRecordSet.getLength());
    }
    if(H.isObject(L.pagination)){
      I.set("rowsPerPage",L.pagination.rowsPerPage);
      I.set("recordOffset",L.pagination.recordOffset);
    }
  }
  if(L.sortedBy){
  this.set("sortedBy",L.sortedBy);
}else{
  if(L.sorting){
    this.set("sortedBy",L.sorting);
  }
}
}
},
showCellEditorBtns:function(K){
  var L=K.appendChild(document.createElement("div"));
  C.addClass(L,D.CLASS_BUTTON);
  var J=L.appendChild(document.createElement("button"));
  C.addClass(J,D.CLASS_DEFAULT);
  J.innerHTML="OK";
  G.addListener(J,"click",function(N,M){
    M.onEventSaveCellEditor(N,M);
    M.focusTbodyEl();
  },this,true);
  var I=L.appendChild(document.createElement("button"));
  I.innerHTML="Cancel";
  G.addListener(I,"click",function(N,M){
    M.onEventCancelCellEditor(N,M);
    M.focusTbodyEl();
  },this,true);
},
resetCellEditor:function(){
  var I=this._oCellEditor.container;
  I.style.display="none";
  G.purgeElement(I,true);
  I.innerHTML="";
  this._oCellEditor.value=null;
  this._oCellEditor.isActive=false;
},
getBody:function(){
  return this.getTbodyEl();
},
getCell:function(I){
  return this.getTdEl(I);
},
getRow:function(I){
  return this.getTrEl(I);
},
refreshView:function(){
  this.render();
},
select:function(J){
  if(!H.isArray(J)){
    J=[J];
  }
  for(var I=0;I<J.length;I++){
    this.selectRow(J[I]);
  }
  },
onEventEditCell:function(I){
  this.onEventShowCellEditor(I);
},
_syncColWidths:function(){
  this.validateColumnWidths();
}
});
D.prototype.onDataReturnSetRecords=D.prototype.onDataReturnSetRows;
D.prototype.onPaginatorChange=D.prototype.onPaginatorChangeRequest;
D.formatTheadCell=function(){};

D.editCheckbox=function(){};

D.editDate=function(){};

D.editDropdown=function(){};

D.editRadio=function(){};

D.editTextarea=function(){};

D.editTextbox=function(){};

})();
(function(){
  var C=YAHOO.lang,F=YAHOO.util,E=YAHOO.widget,A=YAHOO.env.ua,D=F.Dom,J=F.Event,I=F.DataSourceBase,G=E.DataTable,B=E.Paginator;
  E.ScrollingDataTable=function(N,M,K,L){
    L=L||{};

    if(L.scrollable){
      L.scrollable=false;
    }
    E.ScrollingDataTable.superclass.constructor.call(this,N,M,K,L);
    this.subscribe("columnShowEvent",this._onColumnChange);
  };

  var H=E.ScrollingDataTable;
  C.augmentObject(H,{
    CLASS_HEADER:"yui-dt-hd",
    CLASS_BODY:"yui-dt-bd"
  });
  C.extend(H,G,{
    _elHdContainer:null,
    _elHdTable:null,
    _elBdContainer:null,
    _elBdThead:null,
    _elTmpContainer:null,
    _elTmpTable:null,
    _bScrollbarX:null,
    initAttributes:function(K){
      K=K||{};

      H.superclass.initAttributes.call(this,K);
      this.setAttributeConfig("width",{
        value:null,
        validator:C.isString,
        method:function(L){
          if(this._elHdContainer&&this._elBdContainer){
            this._elHdContainer.style.width=L;
            this._elBdContainer.style.width=L;
            this._syncScrollX();
            this._syncScrollOverhang();
          }
        }
      });
  this.setAttributeConfig("height",{
    value:null,
    validator:C.isString,
    method:function(L){
      if(this._elHdContainer&&this._elBdContainer){
        this._elBdContainer.style.height=L;
        this._syncScrollX();
        this._syncScrollY();
        this._syncScrollOverhang();
      }
    }
  });
this.setAttributeConfig("COLOR_COLUMNFILLER",{
  value:"#F2F2F2",
  validator:C.isString,
  method:function(L){
    this._elHdContainer.style.backgroundColor=L;
  }
});
},
_initDomElements:function(K){
  this._initContainerEl(K);
  if(this._elContainer&&this._elHdContainer&&this._elBdContainer){
    this._initTableEl();
    if(this._elHdTable&&this._elTable){
      this._initColgroupEl(this._elHdTable);
      this._initTheadEl(this._elHdTable,this._elTable);
      this._initTbodyEl(this._elTable);
      this._initMsgTbodyEl(this._elTable);
    }
  }
  if(!this._elContainer||!this._elTable||!this._elColgroup||!this._elThead||!this._elTbody||!this._elMsgTbody||!this._elHdTable||!this._elBdThead){
  return false;
}else{
  return true;
}
},
_destroyContainerEl:function(K){
  D.removeClass(K,G.CLASS_SCROLLABLE);
  H.superclass._destroyContainerEl.call(this,K);
  this._elHdContainer=null;
  this._elBdContainer=null;
},
_initContainerEl:function(L){
  H.superclass._initContainerEl.call(this,L);
  if(this._elContainer){
    L=this._elContainer;
    D.addClass(L,G.CLASS_SCROLLABLE);
    var K=document.createElement("div");
    K.style.width=this.get("width")||"";
    K.style.backgroundColor=this.get("COLOR_COLUMNFILLER");
    D.addClass(K,H.CLASS_HEADER);
    this._elHdContainer=K;
    L.appendChild(K);
    var M=document.createElement("div");
    M.style.width=this.get("width")||"";
    M.style.height=this.get("height")||"";
    D.addClass(M,H.CLASS_BODY);
    J.addListener(M,"scroll",this._onScroll,this);
    this._elBdContainer=M;
    L.appendChild(M);
  }
},
_initCaptionEl:function(K){},
_destroyHdTableEl:function(){
  var K=this._elHdTable;
  if(K){
    J.purgeElement(K,true);
    K.parentNode.removeChild(K);
    this._elBdThead=null;
  }
},
_initTableEl:function(){
  if(this._elHdContainer){
    this._destroyHdTableEl();
    this._elHdTable=this._elHdContainer.appendChild(document.createElement("table"));
  }
  H.superclass._initTableEl.call(this,this._elBdContainer);
},
_initTheadEl:function(L,K){
  L=L||this._elHdTable;
  K=K||this._elTable;
  this._initBdTheadEl(K);
  H.superclass._initTheadEl.call(this,L);
},
_initThEl:function(L,K){
  H.superclass._initThEl.call(this,L,K);
  L.id=this.getId()+"-fixedth-"+K.getSanitizedKey();
},
_destroyBdTheadEl:function(){
  var K=this._elBdThead;
  if(K){
    var L=K.parentNode;
    J.purgeElement(K,true);
    L.removeChild(K);
    this._elBdThead=null;
    this._destroyColumnHelpers();
  }
},
_initBdTheadEl:function(S){
  if(S){
    this._destroyBdTheadEl();
    var O=S.insertBefore(document.createElement("thead"),S.firstChild);
    var U=this._oColumnSet,T=U.tree,N,K,R,P,M,L,Q;
    for(P=0,L=T.length;P<L;P++){
      K=O.appendChild(document.createElement("tr"));
      for(M=0,Q=T[P].length;M<Q;M++){
        R=T[P][M];
        N=K.appendChild(document.createElement("th"));
        this._initBdThEl(N,R,P,M);
      }
      }
      this._elBdThead=O;
}
},
_initBdThEl:function(N,M){
  N.id=this.getId()+"-th-"+M.getSanitizedKey();
  N.rowSpan=M.getRowspan();
  N.colSpan=M.getColspan();
  if(M.abbr){
    N.abbr=M.abbr;
  }
  var L=M.getKey();
  var K=C.isValue(M.label)?M.label:L;
  N.innerHTML=K;
},
_initTbodyEl:function(K){
  H.superclass._initTbodyEl.call(this,K);
  K.style.marginTop=(this._elTbody.offsetTop>0)?"-"+this._elTbody.offsetTop+"px":0;
},
_focusEl:function(L){
  L=L||this._elTbody;
  var K=this;
  this._storeScrollPositions();
  setTimeout(function(){
    setTimeout(function(){
      try{
        L.focus();
        K._restoreScrollPositions();
      }catch(M){}
    },0);
  },0);
},
_runRenderChain:function(){
  this._storeScrollPositions();
  this._oChainRender.run();
},
_storeScrollPositions:function(){
  this._nScrollTop=this._elBdContainer.scrollTop;
  this._nScrollLeft=this._elBdContainer.scrollLeft;
},
clearScrollPositions:function(){
  this._nScrollTop=0;
  this._nScrollLeft=0;
},
_restoreScrollPositions:function(){
  if(this._nScrollTop){
    this._elBdContainer.scrollTop=this._nScrollTop;
    this._nScrollTop=null;
  }
  if(this._nScrollLeft){
    this._elBdContainer.scrollLeft=this._nScrollLeft;
    this._nScrollLeft=null;
  }
},
_validateColumnWidth:function(N,K){
  if(!N.width&&!N.hidden){
    var P=N.getThEl();
    if(N._calculatedWidth){
      this._setColumnWidth(N,"auto","visible");
    }
    if(P.offsetWidth!==K.offsetWidth){
      var M=(P.offsetWidth>K.offsetWidth)?N.getThLinerEl():K.firstChild;
      var L=Math.max(0,(M.offsetWidth-(parseInt(D.getStyle(M,"paddingLeft"),10)|0)-(parseInt(D.getStyle(M,"paddingRight"),10)|0)),N.minWidth);
      var O="visible";
      if((N.maxAutoWidth>0)&&(L>N.maxAutoWidth)){
        L=N.maxAutoWidth;
        O="hidden";
      }
      this._elTbody.style.display="none";
      this._setColumnWidth(N,L+"px",O);
      N._calculatedWidth=L;
      this._elTbody.style.display="";
    }
  }
},
validateColumnWidths:function(S){
  var U=this._oColumnSet.keys,W=U.length,L=this.getFirstTrEl();
  if(A.ie){
    this._setOverhangValue(1);
  }
  if(U&&L&&(L.childNodes.length===W)){
    var M=this.get("width");
    if(M){
      this._elHdContainer.style.width="";
      this._elBdContainer.style.width="";
    }
    this._elContainer.style.width="";
    if(S&&C.isNumber(S.getKeyIndex())){
      this._validateColumnWidth(S,L.childNodes[S.getKeyIndex()]);
    }else{
      var T,K=[],O,Q,R;
      for(Q=0;Q<W;Q++){
        S=U[Q];
        if(!S.width&&!S.hidden&&S._calculatedWidth){
          K[K.length]=S;
        }
      }
      this._elTbody.style.display="none";
    for(Q=0,R=K.length;Q<R;Q++){
      this._setColumnWidth(K[Q],"auto","visible");
    }
    this._elTbody.style.display="";
    K=[];
    for(Q=0;Q<W;Q++){
      S=U[Q];
      T=L.childNodes[Q];
      if(!S.width&&!S.hidden){
        var N=S.getThEl();
        if(N.offsetWidth!==T.offsetWidth){
          var V=(N.offsetWidth>T.offsetWidth)?S.getThLinerEl():T.firstChild;
          var P=Math.max(0,(V.offsetWidth-(parseInt(D.getStyle(V,"paddingLeft"),10)|0)-(parseInt(D.getStyle(V,"paddingRight"),10)|0)),S.minWidth);
          var X="visible";
          if((S.maxAutoWidth>0)&&(P>S.maxAutoWidth)){
            P=S.maxAutoWidth;
            X="hidden";
          }
          K[K.length]=[S,P,X];
        }
      }
    }
    this._elTbody.style.display="none";
for(Q=0,R=K.length;Q<R;Q++){
  O=K[Q];
  this._setColumnWidth(O[0],O[1]+"px",O[2]);
  O[0]._calculatedWidth=O[1];
}
this._elTbody.style.display="";
}
if(M){
  this._elHdContainer.style.width=M;
  this._elBdContainer.style.width=M;
}
}
this._syncScroll();
this._restoreScrollPositions();
},
_syncScroll:function(){
  this._syncScrollX();
  this._syncScrollY();
  this._syncScrollOverhang();
  if(A.opera){
    this._elHdContainer.scrollLeft=this._elBdContainer.scrollLeft;
    if(!this.get("width")){
      document.body.style+="";
    }
  }
},
_syncScrollY:function(){
  var K=this._elTbody,L=this._elBdContainer;
  if(!this.get("width")){
    this._elContainer.style.width=(L.scrollHeight>L.clientHeight)?(K.parentNode.clientWidth+19)+"px":(K.parentNode.clientWidth+2)+"px";
  }
},
_syncScrollX:function(){
  var K=this._elTbody,L=this._elBdContainer;
  if(!this.get("height")&&(A.ie)){
    L.style.height=(L.scrollWidth>L.offsetWidth)?(K.parentNode.offsetHeight+18)+"px":K.parentNode.offsetHeight+"px";
  }
  if(this._elTbody.rows.length===0){
    this._elMsgTbody.parentNode.style.width=this.getTheadEl().parentNode.offsetWidth+"px";
  }else{
    this._elMsgTbody.parentNode.style.width="";
  }
},
_syncScrollOverhang:function(){
  var L=this._elBdContainer,K=1;
  if((L.scrollHeight>L.clientHeight)&&(L.scrollWidth>L.clientWidth)){
    K=18;
  }
  this._setOverhangValue(K);
},
_setOverhangValue:function(N){
  var P=this._oColumnSet.headers[this._oColumnSet.headers.length-1]||[],L=P.length,K=this._sId+"-fixedth-",O=N+"px solid "+this.get("COLOR_COLUMNFILLER");
  this._elThead.style.display="none";
  for(var M=0;M<L;M++){
    D.get(K+P[M]).style.borderRight=O;
  }
  this._elThead.style.display="";
},
getHdContainerEl:function(){
  return this._elHdContainer;
},
getBdContainerEl:function(){
  return this._elBdContainer;
},
getHdTableEl:function(){
  return this._elHdTable;
},
getBdTableEl:function(){
  return this._elTable;
},
disable:function(){
  var K=this._elMask;
  K.style.width=this._elBdContainer.offsetWidth+"px";
  K.style.height=this._elHdContainer.offsetHeight+this._elBdContainer.offsetHeight+"px";
  K.style.display="";
  this.fireEvent("disableEvent");
},
removeColumn:function(M){
  var K=this._elHdContainer.scrollLeft;
  var L=this._elBdContainer.scrollLeft;
  M=H.superclass.removeColumn.call(this,M);
  this._elHdContainer.scrollLeft=K;
  this._elBdContainer.scrollLeft=L;
  return M;
},
insertColumn:function(N,L){
  var K=this._elHdContainer.scrollLeft;
  var M=this._elBdContainer.scrollLeft;
  var O=H.superclass.insertColumn.call(this,N,L);
  this._elHdContainer.scrollLeft=K;
  this._elBdContainer.scrollLeft=M;
  return O;
},
reorderColumn:function(N,L){
  var K=this._elHdContainer.scrollLeft;
  var M=this._elBdContainer.scrollLeft;
  var O=H.superclass.reorderColumn.call(this,N,L);
  this._elHdContainer.scrollLeft=K;
  this._elBdContainer.scrollLeft=M;
  return O;
},
setColumnWidth:function(L,K){
  L=this.getColumn(L);
  if(L){
    this._storeScrollPositions();
    if(C.isNumber(K)){
      K=(K>L.minWidth)?K:L.minWidth;
      L.width=K;
      this._setColumnWidth(L,K+"px");
      this._syncScroll();
      this.fireEvent("columnSetWidthEvent",{
        column:L,
        width:K
      });
    }else{
      if(K===null){
        L.width=K;
        this._setColumnWidth(L,"auto");
        this.validateColumnWidths(L);
        this.fireEvent("columnUnsetWidthEvent",{
          column:L
        });
      }
    }
    this._clearTrTemplateEl();
}else{}
},
scrollTo:function(M){
  var L=this.getTdEl(M);
  if(L){
    this.clearScrollPositions();
    this.getBdContainerEl().scrollLeft=L.offsetLeft;
    this.getBdContainerEl().scrollTop=L.parentNode.offsetTop;
  }else{
    var K=this.getTrEl(M);
    if(K){
      this.clearScrollPositions();
      this.getBdContainerEl().scrollTop=K.offsetTop;
    }
  }
},
showTableMessage:function(O,K){
  var P=this._elMsgTd;
  if(C.isString(O)){
    P.firstChild.innerHTML=O;
  }
  if(C.isString(K)){
    D.addClass(P.firstChild,K);
  }
  var N=this.getTheadEl();
  var L=N.parentNode;
  var M=L.offsetWidth;
  this._elMsgTbody.parentNode.style.width=this.getTheadEl().parentNode.offsetWidth+"px";
  this._elMsgTbody.style.display="";
  this.fireEvent("tableMsgShowEvent",{
    html:O,
    className:K
  });
},
_onColumnChange:function(K){
  var L=(K.column)?K.column:(K.editor)?K.editor.column:null;
  this._storeScrollPositions();
  this.validateColumnWidths(L);
},
_onScroll:function(M,L){
  L._elHdContainer.scrollLeft=L._elBdContainer.scrollLeft;
  if(L._oCellEditor&&L._oCellEditor.isActive){
    L.fireEvent("editorBlurEvent",{
      editor:L._oCellEditor
      });
    L.cancelCellEditor();
  }
  var N=J.getTarget(M);
  var K=N.nodeName.toLowerCase();
  L.fireEvent("tableScrollEvent",{
    event:M,
    target:N
  });
},
_onTheadKeydown:function(N,L){
  if(J.getCharCode(N)===9){
    setTimeout(function(){
      if((L instanceof H)&&L._sId){
        L._elBdContainer.scrollLeft=L._elHdContainer.scrollLeft;
      }
    },0);
}
var O=J.getTarget(N);
var K=O.nodeName.toLowerCase();
var M=true;
while(O&&(K!="table")){
  switch(K){
    case"body":
      return;
    case"input":case"textarea":
      break;
    case"thead":
      M=L.fireEvent("theadKeyEvent",{
      target:O,
      event:N
    });
    break;
    default:
      break;
  }
  if(M===false){
    return;
  }else{
    O=O.parentNode;
    if(O){
      K=O.nodeName.toLowerCase();
    }
  }
}
L.fireEvent("tableKeyEvent",{
  target:(O||L._elContainer),
  event:N
});
}
});
})();
(function(){
  var C=YAHOO.lang,F=YAHOO.util,E=YAHOO.widget,B=YAHOO.env.ua,D=F.Dom,I=F.Event,H=E.DataTable;
  E.BaseCellEditor=function(K,J){
    this._sId=this._sId||"yui-ceditor"+YAHOO.widget.BaseCellEditor._nCount++;
    this._sType=K;
    this._initConfigs(J);
    this._initEvents();
    this.render();
  };

  var A=E.BaseCellEditor;
  C.augmentObject(A,{
    _nCount:0,
    CLASS_CELLEDITOR:"yui-ceditor"
  });
  A.prototype={
    _sId:null,
    _sType:null,
    _oDataTable:null,
    _oColumn:null,
    _oRecord:null,
    _elTd:null,
    _elContainer:null,
    _elCancelBtn:null,
    _elSaveBtn:null,
    _initConfigs:function(K){
      if(K&&YAHOO.lang.isObject(K)){
        for(var J in K){
          if(J){
            this[J]=K[J];
          }
        }
        }
      },
_initEvents:function(){
  this.createEvent("showEvent");
  this.createEvent("keydownEvent");
  this.createEvent("invalidDataEvent");
  this.createEvent("revertEvent");
  this.createEvent("saveEvent");
  this.createEvent("cancelEvent");
  this.createEvent("blurEvent");
  this.createEvent("blockEvent");
  this.createEvent("unblockEvent");
},
asyncSubmitter:null,
value:null,
defaultValue:null,
validator:null,
resetInvalidData:true,
isActive:false,
LABEL_SAVE:"Save",
LABEL_CANCEL:"Cancel",
disableBtns:false,
toString:function(){
  return"CellEditor instance "+this._sId;
},
getId:function(){
  return this._sId;
},
getDataTable:function(){
  return this._oDataTable;
},
getColumn:function(){
  return this._oColumn;
},
getRecord:function(){
  return this._oRecord;
},
getTdEl:function(){
  return this._elTd;
},
getContainerEl:function(){
  return this._elContainer;
},
destroy:function(){
  this.unsubscribeAll();
  var K=this.getColumn();
  if(K){
    K.editor=null;
  }
  var J=this.getContainerEl();
  I.purgeElement(J,true);
  J.parentNode.removeChild(J);
},
render:function(){
  if(this._elContainer){
    YAHOO.util.Event.purgeElement(this._elContainer,true);
    this._elContainer.innerHTML="";
  }
  var J=document.createElement("div");
  J.id=this.getId()+"-container";
  J.style.display="none";
  J.tabIndex=0;
  J.className=H.CLASS_EDITOR;
  document.body.insertBefore(J,document.body.firstChild);
  this._elContainer=J;
  I.addListener(J,"keydown",function(M,K){
    if((M.keyCode==27)){
      var L=I.getTarget(M);
      if(L.nodeName&&L.nodeName.toLowerCase()==="select"){
        L.blur();
      }
      K.cancel();
    }
    K.fireEvent("keydownEvent",{
      editor:this,
      event:M
    });
  },this);
  this.renderForm();
  if(!this.disableBtns){
    this.renderBtns();
  }
  this.doAfterRender();
},
renderBtns:function(){
  var L=this.getContainerEl().appendChild(document.createElement("div"));
  L.className=H.CLASS_BUTTON;
  var K=L.appendChild(document.createElement("button"));
  K.className=H.CLASS_DEFAULT;
  K.innerHTML=this.LABEL_SAVE;
  I.addListener(K,"click",function(M){
    this.save();
  },this,true);
  this._elSaveBtn=K;
  var J=L.appendChild(document.createElement("button"));
  J.innerHTML=this.LABEL_CANCEL;
  I.addListener(J,"click",function(M){
    this.cancel();
  },this,true);
  this._elCancelBtn=J;
},
attach:function(N,L){
  if(N instanceof YAHOO.widget.DataTable){
    this._oDataTable=N;
    L=N.getTdEl(L);
    if(L){
      this._elTd=L;
      var M=N.getColumn(L);
      if(M){
        this._oColumn=M;
        var J=N.getRecord(L);
        if(J){
          this._oRecord=J;
          var K=J.getData(this.getColumn().getField());
          this.value=(K!==undefined)?K:this.defaultValue;
          return true;
        }
      }
    }
}
return false;
},
move:function(){
  var M=this.getContainerEl(),L=this.getTdEl(),J=D.getX(L),N=D.getY(L);
  if(isNaN(J)||isNaN(N)){
    var K=this.getDataTable().getTbodyEl();
    J=L.offsetLeft+D.getX(K.parentNode)-K.scrollLeft;
    N=L.offsetTop+D.getY(K.parentNode)-K.scrollTop+this.getDataTable().getTheadEl().offsetHeight;
  }
  M.style.left=J+"px";
  M.style.top=N+"px";
},
show:function(){
  this.resetForm();
  this.isActive=true;
  this.getContainerEl().style.display="";
  this.focus();
  this.fireEvent("showEvent",{
    editor:this
  });
},
block:function(){
  this.fireEvent("blockEvent",{
    editor:this
  });
},
unblock:function(){
  this.fireEvent("unblockEvent",{
    editor:this
  });
},
save:function(){
  var K=this.getInputValue();
  var L=K;
  if(this.validator){
    L=this.validator.call(this.getDataTable(),K,this.value,this);
    if(L===undefined){
      if(this.resetInvalidData){
        this.resetForm();
      }
      this.fireEvent("invalidDataEvent",{
        editor:this,
        oldData:this.value,
        newData:K
      });
      return;
    }
  }
  var M=this;
var J=function(O,N){
  var P=M.value;
  if(O){
    M.value=N;
    M.getDataTable().updateCell(M.getRecord(),M.getColumn(),N);
    M.getContainerEl().style.display="none";
    M.isActive=false;
    M.getDataTable()._oCellEditor=null;
    M.fireEvent("saveEvent",{
      editor:M,
      oldData:P,
      newData:M.value
      });
  }else{
    M.resetForm();
    M.fireEvent("revertEvent",{
      editor:M,
      oldData:P,
      newData:N
    });
  }
  M.unblock();
};

this.block();
if(C.isFunction(this.asyncSubmitter)){
  this.asyncSubmitter.call(this,J,L);
}else{
  J(true,L);
}
},
cancel:function(){
  if(this.isActive){
    this.getContainerEl().style.display="none";
    this.isActive=false;
    this.getDataTable()._oCellEditor=null;
    this.fireEvent("cancelEvent",{
      editor:this
    });
  }else{}
},
renderForm:function(){},
doAfterRender:function(){},
handleDisabledBtns:function(){},
resetForm:function(){},
focus:function(){},
getInputValue:function(){}
};

C.augmentProto(A,F.EventProvider);
E.CheckboxCellEditor=function(J){
  this._sId="yui-checkboxceditor"+YAHOO.widget.BaseCellEditor._nCount++;
  E.CheckboxCellEditor.superclass.constructor.call(this,"checkbox",J);
};

C.extend(E.CheckboxCellEditor,A,{
  checkboxOptions:null,
  checkboxes:null,
  value:null,
  renderForm:function(){
    if(C.isArray(this.checkboxOptions)){
      var M,N,P,K,L,J;
      for(L=0,J=this.checkboxOptions.length;L<J;L++){
        M=this.checkboxOptions[L];
        N=C.isValue(M.value)?M.value:M;
        P=this.getId()+"-chk"+L;
        this.getContainerEl().innerHTML+='<input type="checkbox"'+' id="'+P+'"'+' value="'+N+'" />';
        K=this.getContainerEl().appendChild(document.createElement("label"));
        K.htmlFor=P;
        K.innerHTML=C.isValue(M.label)?M.label:M;
      }
      var O=[];
      for(L=0;L<J;L++){
        O[O.length]=this.getContainerEl().childNodes[L*2];
      }
      this.checkboxes=O;
      if(this.disableBtns){
        this.handleDisabledBtns();
      }
    }else{}
},
handleDisabledBtns:function(){
  I.addListener(this.getContainerEl(),"click",function(J){
    if(I.getTarget(J).tagName.toLowerCase()==="input"){
      this.save();
    }
  },this,true);
},
resetForm:function(){
  var N=C.isArray(this.value)?this.value:[this.value];
  for(var M=0,L=this.checkboxes.length;M<L;M++){
    this.checkboxes[M].checked=false;
    for(var K=0,J=N.length;K<J;K++){
      if(this.checkboxes[M].value===N[K]){
        this.checkboxes[M].checked=true;
      }
    }
    }
  },
focus:function(){
  this.checkboxes[0].focus();
},
getInputValue:function(){
  var J=[];
  for(var L=0,K=this.checkboxes.length;L<K;L++){
    if(this.checkboxes[L].checked){
      J[J.length]=this.checkboxes[L].value;
    }
  }
  return J;
}
});
C.augmentObject(E.CheckboxCellEditor,A);
E.DateCellEditor=function(J){
  this._sId="yui-dateceditor"+YAHOO.widget.BaseCellEditor._nCount++;
  E.DateCellEditor.superclass.constructor.call(this,"date",J);
};

C.extend(E.DateCellEditor,A,{
  calendar:null,
  calendarOptions:null,
  defaultValue:new Date(),
  renderForm:function(){
    if(YAHOO.widget.Calendar){
      var K=this.getContainerEl().appendChild(document.createElement("div"));
      K.id=this.getId()+"-dateContainer";
      var L=new YAHOO.widget.Calendar(this.getId()+"-date",K.id,this.calendarOptions);
      L.render();
      K.style.cssFloat="none";
      if(B.ie){
        var J=this.getContainerEl().appendChild(document.createElement("div"));
        J.style.clear="both";
      }
      this.calendar=L;
      if(this.disableBtns){
        this.handleDisabledBtns();
      }
    }else{}
},
handleDisabledBtns:function(){
  this.calendar.selectEvent.subscribe(function(J){
    this.save();
  },this,true);
},
resetForm:function(){
  var K=this.value;
  var J=(K.getMonth()+1)+"/"+K.getDate()+"/"+K.getFullYear();
  this.calendar.cfg.setProperty("selected",J,false);
  this.calendar.render();
},
focus:function(){},
getInputValue:function(){
  return this.calendar.getSelectedDates()[0];
}
});
C.augmentObject(E.DateCellEditor,A);
E.DropdownCellEditor=function(J){
  this._sId="yui-dropdownceditor"+YAHOO.widget.BaseCellEditor._nCount++;
  E.DropdownCellEditor.superclass.constructor.call(this,"dropdown",J);
};

C.extend(E.DropdownCellEditor,A,{
  dropdownOptions:null,
  dropdown:null,
  multiple:false,
  size:null,
  renderForm:function(){
    var M=this.getContainerEl().appendChild(document.createElement("select"));
    M.style.zoom=1;
    if(this.multiple){
      M.multiple="multiple";
    }
    if(C.isNumber(this.size)){
      M.size=this.size;
    }
    this.dropdown=M;
    if(C.isArray(this.dropdownOptions)){
      var N,L;
      for(var K=0,J=this.dropdownOptions.length;K<J;K++){
        N=this.dropdownOptions[K];
        L=document.createElement("option");
        L.value=(C.isValue(N.value))?N.value:N;
        L.innerHTML=(C.isValue(N.label))?N.label:N;
        L=M.appendChild(L);
      }
      if(this.disableBtns){
        this.handleDisabledBtns();
      }
    }
  },
handleDisabledBtns:function(){
  if(this.multiple){
    I.addListener(this.dropdown,"blur",function(J){
      this.save();
    },this,true);
  }else{
    I.addListener(this.dropdown,"change",function(J){
      this.save();
    },this,true);
  }
},
resetForm:function(){
  var P=this.dropdown.options,M=0,L=P.length;
  if(C.isArray(this.value)){
    var K=this.value,J=0,O=K.length,N={};

    for(;M<L;M++){
      P[M].selected=false;
      N[P[M].value]=P[M];
    }
    for(;J<O;J++){
      if(N[K[J]]){
        N[K[J]].selected=true;
      }
    }
    }else{
  for(;M<L;M++){
    if(this.value===P[M].value){
      P[M].selected=true;
    }
  }
  }
},
focus:function(){
  this.getDataTable()._focusEl(this.dropdown);
},
getInputValue:function(){
  var M=this.dropdown.options;
  if(this.multiple){
    var J=[],L=0,K=M.length;
    for(;L<K;L++){
      if(M[L].selected){
        J.push(M[L].value);
      }
    }
    return J;
}else{
  return M[M.selectedIndex].value;
}
}
});
C.augmentObject(E.DropdownCellEditor,A);
E.RadioCellEditor=function(J){
  this._sId="yui-radioceditor"+YAHOO.widget.BaseCellEditor._nCount++;
  E.RadioCellEditor.superclass.constructor.call(this,"radio",J);
};

C.extend(E.RadioCellEditor,A,{
  radios:null,
  radioOptions:null,
  renderForm:function(){
    if(C.isArray(this.radioOptions)){
      var J,K,Q,N;
      for(var M=0,O=this.radioOptions.length;M<O;M++){
        J=this.radioOptions[M];
        K=C.isValue(J.value)?J.value:J;
        Q=this.getId()+"-radio"+M;
        this.getContainerEl().innerHTML+='<input type="radio"'+' name="'+this.getId()+'"'+' value="'+K+'"'+' id="'+Q+'" />';
        N=this.getContainerEl().appendChild(document.createElement("label"));
        N.htmlFor=Q;
        N.innerHTML=(C.isValue(J.label))?J.label:J;
      }
      var P=[],R;
      for(var L=0;L<O;L++){
        R=this.getContainerEl().childNodes[L*2];
        P[P.length]=R;
      }
      this.radios=P;
      if(this.disableBtns){
        this.handleDisabledBtns();
      }
    }else{}
},
handleDisabledBtns:function(){
  I.addListener(this.getContainerEl(),"click",function(J){
    if(I.getTarget(J).tagName.toLowerCase()==="input"){
      this.save();
    }
  },this,true);
},
resetForm:function(){
  for(var L=0,K=this.radios.length;L<K;L++){
    var J=this.radios[L];
    if(this.value===J.value){
      J.checked=true;
      return;
    }
  }
  },
focus:function(){
  for(var K=0,J=this.radios.length;K<J;K++){
    if(this.radios[K].checked){
      this.radios[K].focus();
      return;
    }
  }
  },
getInputValue:function(){
  for(var K=0,J=this.radios.length;K<J;K++){
    if(this.radios[K].checked){
      return this.radios[K].value;
    }
  }
  }
});
C.augmentObject(E.RadioCellEditor,A);
E.TextareaCellEditor=function(J){
  this._sId="yui-textareaceditor"+YAHOO.widget.BaseCellEditor._nCount++;
  E.TextareaCellEditor.superclass.constructor.call(this,"textarea",J);
};

C.extend(E.TextareaCellEditor,A,{
  textarea:null,
  renderForm:function(){
    var J=this.getContainerEl().appendChild(document.createElement("textarea"));
    this.textarea=J;
    if(this.disableBtns){
      this.handleDisabledBtns();
    }
  },
handleDisabledBtns:function(){
  I.addListener(this.textarea,"blur",function(J){
    this.save();
  },this,true);
},
move:function(){
  this.textarea.style.width=this.getTdEl().offsetWidth+"px";
  this.textarea.style.height="3em";
  YAHOO.widget.TextareaCellEditor.superclass.move.call(this);
},
resetForm:function(){
  this.textarea.value=this.value;
},
focus:function(){
  this.getDataTable()._focusEl(this.textarea);
  this.textarea.select();
},
getInputValue:function(){
  return this.textarea.value;
}
});
C.augmentObject(E.TextareaCellEditor,A);
E.TextboxCellEditor=function(J){
  this._sId="yui-textboxceditor"+YAHOO.widget.BaseCellEditor._nCount++;
  E.TextboxCellEditor.superclass.constructor.call(this,"textbox",J);
};

C.extend(E.TextboxCellEditor,A,{
  textbox:null,
  renderForm:function(){
    var J;
    if(B.webkit>420){
      J=this.getContainerEl().appendChild(document.createElement("form")).appendChild(document.createElement("input"));
    }else{
      J=this.getContainerEl().appendChild(document.createElement("input"));
    }
    J.type="text";
    this.textbox=J;
    I.addListener(J,"keypress",function(K){
      if((K.keyCode===13)){
        YAHOO.util.Event.preventDefault(K);
        this.save();
      }
    },this,true);
  if(this.disableBtns){
    this.handleDisabledBtns();
  }
},
move:function(){
  this.textbox.style.width=this.getTdEl().offsetWidth+"px";
  E.TextboxCellEditor.superclass.move.call(this);
},
resetForm:function(){
  this.textbox.value=C.isValue(this.value)?this.value.toString():"";
},
focus:function(){
  this.getDataTable()._focusEl(this.textbox);
  this.textbox.select();
},
getInputValue:function(){
  return this.textbox.value;
}
});
C.augmentObject(E.TextboxCellEditor,A);
H.Editors={
  checkbox:E.CheckboxCellEditor,
  "date":E.DateCellEditor,
  dropdown:E.DropdownCellEditor,
  radio:E.RadioCellEditor,
  textarea:E.TextareaCellEditor,
  textbox:E.TextboxCellEditor
  };

E.CellEditor=function(K,J){
  if(K&&H.Editors[K]){
    C.augmentObject(A,H.Editors[K]);
    return new H.Editors[K](J);
  }else{
    return new A(null,J);
  }
};

var G=E.CellEditor;
C.augmentObject(G,A);
})();
YAHOO.register("datatable",YAHOO.widget.DataTable,{
  version:"2.8.0r4",
  build:"2449"
});