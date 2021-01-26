/*!
 *   Revision: 3.10.0-812f155
 *   Date: 2020-09-23 22:40:27
 * 
 *   http://www.asperasoft.com
 *   Copyright IBM Corp. 2008, 2020
 */
window["AW4"] =
/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId]) {
/******/ 			return installedModules[moduleId].exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, { enumerable: true, get: getter });
/******/ 		}
/******/ 	};
/******/
/******/ 	// define __esModule on exports
/******/ 	__webpack_require__.r = function(exports) {
/******/ 		if(typeof Symbol !== 'undefined' && Symbol.toStringTag) {
/******/ 			Object.defineProperty(exports, Symbol.toStringTag, { value: 'Module' });
/******/ 		}
/******/ 		Object.defineProperty(exports, '__esModule', { value: true });
/******/ 	};
/******/
/******/ 	// create a fake namespace object
/******/ 	// mode & 1: value is a module id, require it
/******/ 	// mode & 2: merge all properties of value into the ns
/******/ 	// mode & 4: return value when already ns object
/******/ 	// mode & 8|1: behave like require
/******/ 	__webpack_require__.t = function(value, mode) {
/******/ 		if(mode & 1) value = __webpack_require__(value);
/******/ 		if(mode & 8) return value;
/******/ 		if((mode & 4) && typeof value === 'object' && value && value.__esModule) return value;
/******/ 		var ns = Object.create(null);
/******/ 		__webpack_require__.r(ns);
/******/ 		Object.defineProperty(ns, 'default', { enumerable: true, value: value });
/******/ 		if(mode & 2 && typeof value != 'string') for(var key in value) __webpack_require__.d(ns, key, function(key) { return value[key]; }.bind(null, key));
/******/ 		return ns;
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";
/******/
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = "./src/index.ts");
/******/ })
/************************************************************************/
/******/ ({

/***/ "./node_modules/core-js/es/array/find.js":
/*!***********************************************!*\
  !*** ./node_modules/core-js/es/array/find.js ***!
  \***********************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

__webpack_require__(/*! ../../modules/es.array.find */ "./node_modules/core-js/modules/es.array.find.js");

module.exports = __webpack_require__(/*! ../../internals/entry-unbind */ "./node_modules/core-js/internals/entry-unbind.js")('Array', 'find');


/***/ }),

/***/ "./node_modules/core-js/es/object/assign.js":
/*!**************************************************!*\
  !*** ./node_modules/core-js/es/object/assign.js ***!
  \**************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

__webpack_require__(/*! ../../modules/es.object.assign */ "./node_modules/core-js/modules/es.object.assign.js");

module.exports = __webpack_require__(/*! ../../internals/path */ "./node_modules/core-js/internals/path.js").Object.assign;


/***/ }),

/***/ "./node_modules/core-js/es/promise/index.js":
/*!**************************************************!*\
  !*** ./node_modules/core-js/es/promise/index.js ***!
  \**************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

__webpack_require__(/*! ../../modules/es.object.to-string */ "./node_modules/core-js/modules/es.object.to-string.js");
__webpack_require__(/*! ../../modules/es.string.iterator */ "./node_modules/core-js/modules/es.string.iterator.js");
__webpack_require__(/*! ../../modules/web.dom-collections.iterator */ "./node_modules/core-js/modules/web.dom-collections.iterator.js");
__webpack_require__(/*! ../../modules/es.promise */ "./node_modules/core-js/modules/es.promise.js");
__webpack_require__(/*! ../../modules/es.promise.finally */ "./node_modules/core-js/modules/es.promise.finally.js");

module.exports = __webpack_require__(/*! ../../internals/path */ "./node_modules/core-js/internals/path.js").Promise;


/***/ }),

/***/ "./node_modules/core-js/features/array/find.js":
/*!*****************************************************!*\
  !*** ./node_modules/core-js/features/array/find.js ***!
  \*****************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__(/*! ../../es/array/find */ "./node_modules/core-js/es/array/find.js");


/***/ }),

/***/ "./node_modules/core-js/features/object/assign.js":
/*!********************************************************!*\
  !*** ./node_modules/core-js/features/object/assign.js ***!
  \********************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__(/*! ../../es/object/assign */ "./node_modules/core-js/es/object/assign.js");


/***/ }),

/***/ "./node_modules/core-js/features/promise/index.js":
/*!********************************************************!*\
  !*** ./node_modules/core-js/features/promise/index.js ***!
  \********************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__(/*! ../../es/promise */ "./node_modules/core-js/es/promise/index.js");

__webpack_require__(/*! ../../modules/esnext.aggregate-error */ "./node_modules/core-js/modules/esnext.aggregate-error.js");
__webpack_require__(/*! ../../modules/esnext.promise.all-settled */ "./node_modules/core-js/modules/esnext.promise.all-settled.js");
__webpack_require__(/*! ../../modules/esnext.promise.try */ "./node_modules/core-js/modules/esnext.promise.try.js");
__webpack_require__(/*! ../../modules/esnext.promise.any */ "./node_modules/core-js/modules/esnext.promise.any.js");


/***/ }),

/***/ "./node_modules/core-js/internals/a-function.js":
/*!******************************************************!*\
  !*** ./node_modules/core-js/internals/a-function.js ***!
  \******************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = function (it) {
  if (typeof it != 'function') {
    throw TypeError(String(it) + ' is not a function');
  } return it;
};


/***/ }),

/***/ "./node_modules/core-js/internals/add-to-unscopables.js":
/*!**************************************************************!*\
  !*** ./node_modules/core-js/internals/add-to-unscopables.js ***!
  \**************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var UNSCOPABLES = __webpack_require__(/*! ../internals/well-known-symbol */ "./node_modules/core-js/internals/well-known-symbol.js")('unscopables');
var create = __webpack_require__(/*! ../internals/object-create */ "./node_modules/core-js/internals/object-create.js");
var hide = __webpack_require__(/*! ../internals/hide */ "./node_modules/core-js/internals/hide.js");
var ArrayPrototype = Array.prototype;

// Array.prototype[@@unscopables]
// https://tc39.github.io/ecma262/#sec-array.prototype-@@unscopables
if (ArrayPrototype[UNSCOPABLES] == undefined) {
  hide(ArrayPrototype, UNSCOPABLES, create(null));
}

// add a key to Array.prototype[@@unscopables]
module.exports = function (key) {
  ArrayPrototype[UNSCOPABLES][key] = true;
};


/***/ }),

/***/ "./node_modules/core-js/internals/an-instance.js":
/*!*******************************************************!*\
  !*** ./node_modules/core-js/internals/an-instance.js ***!
  \*******************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = function (it, Constructor, name) {
  if (!(it instanceof Constructor)) {
    throw TypeError('Incorrect ' + (name ? name + ' ' : '') + 'invocation');
  } return it;
};


/***/ }),

/***/ "./node_modules/core-js/internals/an-object.js":
/*!*****************************************************!*\
  !*** ./node_modules/core-js/internals/an-object.js ***!
  \*****************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var isObject = __webpack_require__(/*! ../internals/is-object */ "./node_modules/core-js/internals/is-object.js");

module.exports = function (it) {
  if (!isObject(it)) {
    throw TypeError(String(it) + ' is not an object');
  } return it;
};


/***/ }),

/***/ "./node_modules/core-js/internals/array-includes.js":
/*!**********************************************************!*\
  !*** ./node_modules/core-js/internals/array-includes.js ***!
  \**********************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var toIndexedObject = __webpack_require__(/*! ../internals/to-indexed-object */ "./node_modules/core-js/internals/to-indexed-object.js");
var toLength = __webpack_require__(/*! ../internals/to-length */ "./node_modules/core-js/internals/to-length.js");
var toAbsoluteIndex = __webpack_require__(/*! ../internals/to-absolute-index */ "./node_modules/core-js/internals/to-absolute-index.js");

// `Array.prototype.{ indexOf, includes }` methods implementation
// false -> Array#indexOf
// https://tc39.github.io/ecma262/#sec-array.prototype.indexof
// true  -> Array#includes
// https://tc39.github.io/ecma262/#sec-array.prototype.includes
module.exports = function (IS_INCLUDES) {
  return function ($this, el, fromIndex) {
    var O = toIndexedObject($this);
    var length = toLength(O.length);
    var index = toAbsoluteIndex(fromIndex, length);
    var value;
    // Array#includes uses SameValueZero equality algorithm
    // eslint-disable-next-line no-self-compare
    if (IS_INCLUDES && el != el) while (length > index) {
      value = O[index++];
      // eslint-disable-next-line no-self-compare
      if (value != value) return true;
    // Array#indexOf ignores holes, Array#includes - not
    } else for (;length > index; index++) if (IS_INCLUDES || index in O) {
      if (O[index] === el) return IS_INCLUDES || index || 0;
    } return !IS_INCLUDES && -1;
  };
};


/***/ }),

/***/ "./node_modules/core-js/internals/array-methods.js":
/*!*********************************************************!*\
  !*** ./node_modules/core-js/internals/array-methods.js ***!
  \*********************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var bind = __webpack_require__(/*! ../internals/bind-context */ "./node_modules/core-js/internals/bind-context.js");
var IndexedObject = __webpack_require__(/*! ../internals/indexed-object */ "./node_modules/core-js/internals/indexed-object.js");
var toObject = __webpack_require__(/*! ../internals/to-object */ "./node_modules/core-js/internals/to-object.js");
var toLength = __webpack_require__(/*! ../internals/to-length */ "./node_modules/core-js/internals/to-length.js");
var arraySpeciesCreate = __webpack_require__(/*! ../internals/array-species-create */ "./node_modules/core-js/internals/array-species-create.js");

// `Array.prototype.{ forEach, map, filter, some, every, find, findIndex }` methods implementation
// 0 -> Array#forEach
// https://tc39.github.io/ecma262/#sec-array.prototype.foreach
// 1 -> Array#map
// https://tc39.github.io/ecma262/#sec-array.prototype.map
// 2 -> Array#filter
// https://tc39.github.io/ecma262/#sec-array.prototype.filter
// 3 -> Array#some
// https://tc39.github.io/ecma262/#sec-array.prototype.some
// 4 -> Array#every
// https://tc39.github.io/ecma262/#sec-array.prototype.every
// 5 -> Array#find
// https://tc39.github.io/ecma262/#sec-array.prototype.find
// 6 -> Array#findIndex
// https://tc39.github.io/ecma262/#sec-array.prototype.findIndex
module.exports = function (TYPE, specificCreate) {
  var IS_MAP = TYPE == 1;
  var IS_FILTER = TYPE == 2;
  var IS_SOME = TYPE == 3;
  var IS_EVERY = TYPE == 4;
  var IS_FIND_INDEX = TYPE == 6;
  var NO_HOLES = TYPE == 5 || IS_FIND_INDEX;
  var create = specificCreate || arraySpeciesCreate;
  return function ($this, callbackfn, that) {
    var O = toObject($this);
    var self = IndexedObject(O);
    var boundFunction = bind(callbackfn, that, 3);
    var length = toLength(self.length);
    var index = 0;
    var target = IS_MAP ? create($this, length) : IS_FILTER ? create($this, 0) : undefined;
    var value, result;
    for (;length > index; index++) if (NO_HOLES || index in self) {
      value = self[index];
      result = boundFunction(value, index, O);
      if (TYPE) {
        if (IS_MAP) target[index] = result; // map
        else if (result) switch (TYPE) {
          case 3: return true;              // some
          case 5: return value;             // find
          case 6: return index;             // findIndex
          case 2: target.push(value);       // filter
        } else if (IS_EVERY) return false;  // every
      }
    }
    return IS_FIND_INDEX ? -1 : IS_SOME || IS_EVERY ? IS_EVERY : target;
  };
};


/***/ }),

/***/ "./node_modules/core-js/internals/array-species-create.js":
/*!****************************************************************!*\
  !*** ./node_modules/core-js/internals/array-species-create.js ***!
  \****************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var isObject = __webpack_require__(/*! ../internals/is-object */ "./node_modules/core-js/internals/is-object.js");
var isArray = __webpack_require__(/*! ../internals/is-array */ "./node_modules/core-js/internals/is-array.js");
var SPECIES = __webpack_require__(/*! ../internals/well-known-symbol */ "./node_modules/core-js/internals/well-known-symbol.js")('species');

// `ArraySpeciesCreate` abstract operation
// https://tc39.github.io/ecma262/#sec-arrayspeciescreate
module.exports = function (originalArray, length) {
  var C;
  if (isArray(originalArray)) {
    C = originalArray.constructor;
    // cross-realm fallback
    if (typeof C == 'function' && (C === Array || isArray(C.prototype))) C = undefined;
    else if (isObject(C)) {
      C = C[SPECIES];
      if (C === null) C = undefined;
    }
  } return new (C === undefined ? Array : C)(length === 0 ? 0 : length);
};


/***/ }),

/***/ "./node_modules/core-js/internals/bind-context.js":
/*!********************************************************!*\
  !*** ./node_modules/core-js/internals/bind-context.js ***!
  \********************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var aFunction = __webpack_require__(/*! ../internals/a-function */ "./node_modules/core-js/internals/a-function.js");

// optional / simple context binding
module.exports = function (fn, that, length) {
  aFunction(fn);
  if (that === undefined) return fn;
  switch (length) {
    case 0: return function () {
      return fn.call(that);
    };
    case 1: return function (a) {
      return fn.call(that, a);
    };
    case 2: return function (a, b) {
      return fn.call(that, a, b);
    };
    case 3: return function (a, b, c) {
      return fn.call(that, a, b, c);
    };
  }
  return function (/* ...args */) {
    return fn.apply(that, arguments);
  };
};


/***/ }),

/***/ "./node_modules/core-js/internals/call-with-safe-iteration-closing.js":
/*!****************************************************************************!*\
  !*** ./node_modules/core-js/internals/call-with-safe-iteration-closing.js ***!
  \****************************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var anObject = __webpack_require__(/*! ../internals/an-object */ "./node_modules/core-js/internals/an-object.js");

// call something on iterator step with safe closing on error
module.exports = function (iterator, fn, value, ENTRIES) {
  try {
    return ENTRIES ? fn(anObject(value)[0], value[1]) : fn(value);
  // 7.4.6 IteratorClose(iterator, completion)
  } catch (error) {
    var returnMethod = iterator['return'];
    if (returnMethod !== undefined) anObject(returnMethod.call(iterator));
    throw error;
  }
};


/***/ }),

/***/ "./node_modules/core-js/internals/check-correctness-of-iteration.js":
/*!**************************************************************************!*\
  !*** ./node_modules/core-js/internals/check-correctness-of-iteration.js ***!
  \**************************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var ITERATOR = __webpack_require__(/*! ../internals/well-known-symbol */ "./node_modules/core-js/internals/well-known-symbol.js")('iterator');
var SAFE_CLOSING = false;

try {
  var called = 0;
  var iteratorWithReturn = {
    next: function () {
      return { done: !!called++ };
    },
    'return': function () {
      SAFE_CLOSING = true;
    }
  };
  iteratorWithReturn[ITERATOR] = function () {
    return this;
  };
  // eslint-disable-next-line no-throw-literal
  Array.from(iteratorWithReturn, function () { throw 2; });
} catch (error) { /* empty */ }

module.exports = function (exec, SKIP_CLOSING) {
  if (!SKIP_CLOSING && !SAFE_CLOSING) return false;
  var ITERATION_SUPPORT = false;
  try {
    var object = {};
    object[ITERATOR] = function () {
      return {
        next: function () {
          return { done: ITERATION_SUPPORT = true };
        }
      };
    };
    exec(object);
  } catch (error) { /* empty */ }
  return ITERATION_SUPPORT;
};


/***/ }),

/***/ "./node_modules/core-js/internals/classof-raw.js":
/*!*******************************************************!*\
  !*** ./node_modules/core-js/internals/classof-raw.js ***!
  \*******************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

var toString = {}.toString;

module.exports = function (it) {
  return toString.call(it).slice(8, -1);
};


/***/ }),

/***/ "./node_modules/core-js/internals/classof.js":
/*!***************************************************!*\
  !*** ./node_modules/core-js/internals/classof.js ***!
  \***************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var classofRaw = __webpack_require__(/*! ../internals/classof-raw */ "./node_modules/core-js/internals/classof-raw.js");
var TO_STRING_TAG = __webpack_require__(/*! ../internals/well-known-symbol */ "./node_modules/core-js/internals/well-known-symbol.js")('toStringTag');
// ES3 wrong here
var CORRECT_ARGUMENTS = classofRaw(function () { return arguments; }()) == 'Arguments';

// fallback for IE11 Script Access Denied error
var tryGet = function (it, key) {
  try {
    return it[key];
  } catch (error) { /* empty */ }
};

// getting tag from ES6+ `Object.prototype.toString`
module.exports = function (it) {
  var O, tag, result;
  return it === undefined ? 'Undefined' : it === null ? 'Null'
    // @@toStringTag case
    : typeof (tag = tryGet(O = Object(it), TO_STRING_TAG)) == 'string' ? tag
    // builtinTag case
    : CORRECT_ARGUMENTS ? classofRaw(O)
    // ES3 arguments fallback
    : (result = classofRaw(O)) == 'Object' && typeof O.callee == 'function' ? 'Arguments' : result;
};


/***/ }),

/***/ "./node_modules/core-js/internals/copy-constructor-properties.js":
/*!***********************************************************************!*\
  !*** ./node_modules/core-js/internals/copy-constructor-properties.js ***!
  \***********************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var has = __webpack_require__(/*! ../internals/has */ "./node_modules/core-js/internals/has.js");
var ownKeys = __webpack_require__(/*! ../internals/own-keys */ "./node_modules/core-js/internals/own-keys.js");
var getOwnPropertyDescriptorModule = __webpack_require__(/*! ../internals/object-get-own-property-descriptor */ "./node_modules/core-js/internals/object-get-own-property-descriptor.js");
var definePropertyModule = __webpack_require__(/*! ../internals/object-define-property */ "./node_modules/core-js/internals/object-define-property.js");

module.exports = function (target, source) {
  var keys = ownKeys(source);
  var defineProperty = definePropertyModule.f;
  var getOwnPropertyDescriptor = getOwnPropertyDescriptorModule.f;
  for (var i = 0; i < keys.length; i++) {
    var key = keys[i];
    if (!has(target, key)) defineProperty(target, key, getOwnPropertyDescriptor(source, key));
  }
};


/***/ }),

/***/ "./node_modules/core-js/internals/correct-prototype-getter.js":
/*!********************************************************************!*\
  !*** ./node_modules/core-js/internals/correct-prototype-getter.js ***!
  \********************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

module.exports = !__webpack_require__(/*! ../internals/fails */ "./node_modules/core-js/internals/fails.js")(function () {
  function F() { /* empty */ }
  F.prototype.constructor = null;
  return Object.getPrototypeOf(new F()) !== F.prototype;
});


/***/ }),

/***/ "./node_modules/core-js/internals/create-iterator-constructor.js":
/*!***********************************************************************!*\
  !*** ./node_modules/core-js/internals/create-iterator-constructor.js ***!
  \***********************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var IteratorPrototype = __webpack_require__(/*! ../internals/iterators-core */ "./node_modules/core-js/internals/iterators-core.js").IteratorPrototype;
var create = __webpack_require__(/*! ../internals/object-create */ "./node_modules/core-js/internals/object-create.js");
var createPropertyDescriptor = __webpack_require__(/*! ../internals/create-property-descriptor */ "./node_modules/core-js/internals/create-property-descriptor.js");
var setToStringTag = __webpack_require__(/*! ../internals/set-to-string-tag */ "./node_modules/core-js/internals/set-to-string-tag.js");
var Iterators = __webpack_require__(/*! ../internals/iterators */ "./node_modules/core-js/internals/iterators.js");

var returnThis = function () { return this; };

module.exports = function (IteratorConstructor, NAME, next) {
  var TO_STRING_TAG = NAME + ' Iterator';
  IteratorConstructor.prototype = create(IteratorPrototype, { next: createPropertyDescriptor(1, next) });
  setToStringTag(IteratorConstructor, TO_STRING_TAG, false, true);
  Iterators[TO_STRING_TAG] = returnThis;
  return IteratorConstructor;
};


/***/ }),

/***/ "./node_modules/core-js/internals/create-property-descriptor.js":
/*!**********************************************************************!*\
  !*** ./node_modules/core-js/internals/create-property-descriptor.js ***!
  \**********************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = function (bitmap, value) {
  return {
    enumerable: !(bitmap & 1),
    configurable: !(bitmap & 2),
    writable: !(bitmap & 4),
    value: value
  };
};


/***/ }),

/***/ "./node_modules/core-js/internals/define-iterator.js":
/*!***********************************************************!*\
  !*** ./node_modules/core-js/internals/define-iterator.js ***!
  \***********************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var $export = __webpack_require__(/*! ../internals/export */ "./node_modules/core-js/internals/export.js");
var createIteratorConstructor = __webpack_require__(/*! ../internals/create-iterator-constructor */ "./node_modules/core-js/internals/create-iterator-constructor.js");
var getPrototypeOf = __webpack_require__(/*! ../internals/object-get-prototype-of */ "./node_modules/core-js/internals/object-get-prototype-of.js");
var setPrototypeOf = __webpack_require__(/*! ../internals/object-set-prototype-of */ "./node_modules/core-js/internals/object-set-prototype-of.js");
var setToStringTag = __webpack_require__(/*! ../internals/set-to-string-tag */ "./node_modules/core-js/internals/set-to-string-tag.js");
var hide = __webpack_require__(/*! ../internals/hide */ "./node_modules/core-js/internals/hide.js");
var redefine = __webpack_require__(/*! ../internals/redefine */ "./node_modules/core-js/internals/redefine.js");
var IS_PURE = __webpack_require__(/*! ../internals/is-pure */ "./node_modules/core-js/internals/is-pure.js");
var ITERATOR = __webpack_require__(/*! ../internals/well-known-symbol */ "./node_modules/core-js/internals/well-known-symbol.js")('iterator');
var Iterators = __webpack_require__(/*! ../internals/iterators */ "./node_modules/core-js/internals/iterators.js");
var IteratorsCore = __webpack_require__(/*! ../internals/iterators-core */ "./node_modules/core-js/internals/iterators-core.js");
var IteratorPrototype = IteratorsCore.IteratorPrototype;
var BUGGY_SAFARI_ITERATORS = IteratorsCore.BUGGY_SAFARI_ITERATORS;
var KEYS = 'keys';
var VALUES = 'values';
var ENTRIES = 'entries';

var returnThis = function () { return this; };

module.exports = function (Iterable, NAME, IteratorConstructor, next, DEFAULT, IS_SET, FORCED) {
  createIteratorConstructor(IteratorConstructor, NAME, next);

  var getIterationMethod = function (KIND) {
    if (KIND === DEFAULT && defaultIterator) return defaultIterator;
    if (!BUGGY_SAFARI_ITERATORS && KIND in IterablePrototype) return IterablePrototype[KIND];
    switch (KIND) {
      case KEYS: return function keys() { return new IteratorConstructor(this, KIND); };
      case VALUES: return function values() { return new IteratorConstructor(this, KIND); };
      case ENTRIES: return function entries() { return new IteratorConstructor(this, KIND); };
    } return function () { return new IteratorConstructor(this); };
  };

  var TO_STRING_TAG = NAME + ' Iterator';
  var INCORRECT_VALUES_NAME = false;
  var IterablePrototype = Iterable.prototype;
  var nativeIterator = IterablePrototype[ITERATOR]
    || IterablePrototype['@@iterator']
    || DEFAULT && IterablePrototype[DEFAULT];
  var defaultIterator = !BUGGY_SAFARI_ITERATORS && nativeIterator || getIterationMethod(DEFAULT);
  var anyNativeIterator = NAME == 'Array' ? IterablePrototype.entries || nativeIterator : nativeIterator;
  var CurrentIteratorPrototype, methods, KEY;

  // fix native
  if (anyNativeIterator) {
    CurrentIteratorPrototype = getPrototypeOf(anyNativeIterator.call(new Iterable()));
    if (IteratorPrototype !== Object.prototype && CurrentIteratorPrototype.next) {
      if (!IS_PURE && getPrototypeOf(CurrentIteratorPrototype) !== IteratorPrototype) {
        if (setPrototypeOf) {
          setPrototypeOf(CurrentIteratorPrototype, IteratorPrototype);
        } else if (typeof CurrentIteratorPrototype[ITERATOR] != 'function') {
          hide(CurrentIteratorPrototype, ITERATOR, returnThis);
        }
      }
      // Set @@toStringTag to native iterators
      setToStringTag(CurrentIteratorPrototype, TO_STRING_TAG, true, true);
      if (IS_PURE) Iterators[TO_STRING_TAG] = returnThis;
    }
  }

  // fix Array#{values, @@iterator}.name in V8 / FF
  if (DEFAULT == VALUES && nativeIterator && nativeIterator.name !== VALUES) {
    INCORRECT_VALUES_NAME = true;
    defaultIterator = function values() { return nativeIterator.call(this); };
  }

  // define iterator
  if ((!IS_PURE || FORCED) && IterablePrototype[ITERATOR] !== defaultIterator) {
    hide(IterablePrototype, ITERATOR, defaultIterator);
  }
  Iterators[NAME] = defaultIterator;

  // export additional methods
  if (DEFAULT) {
    methods = {
      values: getIterationMethod(VALUES),
      keys: IS_SET ? defaultIterator : getIterationMethod(KEYS),
      entries: getIterationMethod(ENTRIES)
    };
    if (FORCED) for (KEY in methods) {
      if (BUGGY_SAFARI_ITERATORS || INCORRECT_VALUES_NAME || !(KEY in IterablePrototype)) {
        redefine(IterablePrototype, KEY, methods[KEY]);
      }
    } else $export({ target: NAME, proto: true, forced: BUGGY_SAFARI_ITERATORS || INCORRECT_VALUES_NAME }, methods);
  }

  return methods;
};


/***/ }),

/***/ "./node_modules/core-js/internals/descriptors.js":
/*!*******************************************************!*\
  !*** ./node_modules/core-js/internals/descriptors.js ***!
  \*******************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

// Thank's IE8 for his funny defineProperty
module.exports = !__webpack_require__(/*! ../internals/fails */ "./node_modules/core-js/internals/fails.js")(function () {
  return Object.defineProperty({}, 'a', { get: function () { return 7; } }).a != 7;
});


/***/ }),

/***/ "./node_modules/core-js/internals/document-create-element.js":
/*!*******************************************************************!*\
  !*** ./node_modules/core-js/internals/document-create-element.js ***!
  \*******************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var isObject = __webpack_require__(/*! ../internals/is-object */ "./node_modules/core-js/internals/is-object.js");
var document = __webpack_require__(/*! ../internals/global */ "./node_modules/core-js/internals/global.js").document;
// typeof document.createElement is 'object' in old IE
var exist = isObject(document) && isObject(document.createElement);

module.exports = function (it) {
  return exist ? document.createElement(it) : {};
};


/***/ }),

/***/ "./node_modules/core-js/internals/dom-iterables.js":
/*!*********************************************************!*\
  !*** ./node_modules/core-js/internals/dom-iterables.js ***!
  \*********************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

// iterable DOM collections
// flag - `iterable` interface - 'entries', 'keys', 'values', 'forEach' methods
module.exports = {
  CSSRuleList: 0,
  CSSStyleDeclaration: 0,
  CSSValueList: 0,
  ClientRectList: 0,
  DOMRectList: 0,
  DOMStringList: 0,
  DOMTokenList: 1,
  DataTransferItemList: 0,
  FileList: 0,
  HTMLAllCollection: 0,
  HTMLCollection: 0,
  HTMLFormElement: 0,
  HTMLSelectElement: 0,
  MediaList: 0,
  MimeTypeArray: 0,
  NamedNodeMap: 0,
  NodeList: 1,
  PaintRequestList: 0,
  Plugin: 0,
  PluginArray: 0,
  SVGLengthList: 0,
  SVGNumberList: 0,
  SVGPathSegList: 0,
  SVGPointList: 0,
  SVGStringList: 0,
  SVGTransformList: 0,
  SourceBufferList: 0,
  StyleSheetList: 0,
  TextTrackCueList: 0,
  TextTrackList: 0,
  TouchList: 0
};


/***/ }),

/***/ "./node_modules/core-js/internals/entry-unbind.js":
/*!********************************************************!*\
  !*** ./node_modules/core-js/internals/entry-unbind.js ***!
  \********************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(/*! ../internals/global */ "./node_modules/core-js/internals/global.js");
var bind = __webpack_require__(/*! ../internals/bind-context */ "./node_modules/core-js/internals/bind-context.js");
var call = Function.call;

module.exports = function (CONSTRUCTOR, METHOD, length) {
  return bind(call, global[CONSTRUCTOR].prototype[METHOD], length);
};


/***/ }),

/***/ "./node_modules/core-js/internals/enum-bug-keys.js":
/*!*********************************************************!*\
  !*** ./node_modules/core-js/internals/enum-bug-keys.js ***!
  \*********************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

// IE8- don't enum bug keys
module.exports = [
  'constructor',
  'hasOwnProperty',
  'isPrototypeOf',
  'propertyIsEnumerable',
  'toLocaleString',
  'toString',
  'valueOf'
];


/***/ }),

/***/ "./node_modules/core-js/internals/export.js":
/*!**************************************************!*\
  !*** ./node_modules/core-js/internals/export.js ***!
  \**************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(/*! ../internals/global */ "./node_modules/core-js/internals/global.js");
var getOwnPropertyDescriptor = __webpack_require__(/*! ../internals/object-get-own-property-descriptor */ "./node_modules/core-js/internals/object-get-own-property-descriptor.js").f;
var hide = __webpack_require__(/*! ../internals/hide */ "./node_modules/core-js/internals/hide.js");
var redefine = __webpack_require__(/*! ../internals/redefine */ "./node_modules/core-js/internals/redefine.js");
var setGlobal = __webpack_require__(/*! ../internals/set-global */ "./node_modules/core-js/internals/set-global.js");
var copyConstructorProperties = __webpack_require__(/*! ../internals/copy-constructor-properties */ "./node_modules/core-js/internals/copy-constructor-properties.js");
var isForced = __webpack_require__(/*! ../internals/is-forced */ "./node_modules/core-js/internals/is-forced.js");

/*
  options.target      - name of the target object
  options.global      - target is the global object
  options.stat        - export as static methods of target
  options.proto       - export as prototype methods of target
  options.real        - real prototype method for the `pure` version
  options.forced      - export even if the native feature is available
  options.bind        - bind methods to the target, required for the `pure` version
  options.wrap        - wrap constructors to preventing global pollution, required for the `pure` version
  options.unsafe      - use the simple assignment of property instead of delete + defineProperty
  options.sham        - add a flag to not completely full polyfills
  options.enumerable  - export as enumerable property
  options.noTargetGet - prevent calling a getter on target
*/
module.exports = function (options, source) {
  var TARGET = options.target;
  var GLOBAL = options.global;
  var STATIC = options.stat;
  var FORCED, target, key, targetProperty, sourceProperty, descriptor;
  if (GLOBAL) {
    target = global;
  } else if (STATIC) {
    target = global[TARGET] || setGlobal(TARGET, {});
  } else {
    target = (global[TARGET] || {}).prototype;
  }
  if (target) for (key in source) {
    sourceProperty = source[key];
    if (options.noTargetGet) {
      descriptor = getOwnPropertyDescriptor(target, key);
      targetProperty = descriptor && descriptor.value;
    } else targetProperty = target[key];
    FORCED = isForced(GLOBAL ? key : TARGET + (STATIC ? '.' : '#') + key, options.forced);
    // contained in target
    if (!FORCED && targetProperty !== undefined) {
      if (typeof sourceProperty === typeof targetProperty) continue;
      copyConstructorProperties(sourceProperty, targetProperty);
    }
    // add a flag to not completely full polyfills
    if (options.sham || (targetProperty && targetProperty.sham)) {
      hide(sourceProperty, 'sham', true);
    }
    // extend global
    redefine(target, key, sourceProperty, options);
  }
};


/***/ }),

/***/ "./node_modules/core-js/internals/fails.js":
/*!*************************************************!*\
  !*** ./node_modules/core-js/internals/fails.js ***!
  \*************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = function (exec) {
  try {
    return !!exec();
  } catch (error) {
    return true;
  }
};


/***/ }),

/***/ "./node_modules/core-js/internals/function-to-string.js":
/*!**************************************************************!*\
  !*** ./node_modules/core-js/internals/function-to-string.js ***!
  \**************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__(/*! ../internals/shared */ "./node_modules/core-js/internals/shared.js")('native-function-to-string', Function.toString);


/***/ }),

/***/ "./node_modules/core-js/internals/get-built-in.js":
/*!********************************************************!*\
  !*** ./node_modules/core-js/internals/get-built-in.js ***!
  \********************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var path = __webpack_require__(/*! ../internals/path */ "./node_modules/core-js/internals/path.js");
var global = __webpack_require__(/*! ../internals/global */ "./node_modules/core-js/internals/global.js");

var aFunction = function (variable) {
  return typeof variable == 'function' ? variable : undefined;
};

module.exports = function (namespace, method) {
  return arguments.length < 2 ? aFunction(path[namespace]) || aFunction(global[namespace])
    : path[namespace] && path[namespace][method] || global[namespace] && global[namespace][method];
};


/***/ }),

/***/ "./node_modules/core-js/internals/get-iterator-method.js":
/*!***************************************************************!*\
  !*** ./node_modules/core-js/internals/get-iterator-method.js ***!
  \***************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var classof = __webpack_require__(/*! ../internals/classof */ "./node_modules/core-js/internals/classof.js");
var ITERATOR = __webpack_require__(/*! ../internals/well-known-symbol */ "./node_modules/core-js/internals/well-known-symbol.js")('iterator');
var Iterators = __webpack_require__(/*! ../internals/iterators */ "./node_modules/core-js/internals/iterators.js");

module.exports = function (it) {
  if (it != undefined) return it[ITERATOR]
    || it['@@iterator']
    || Iterators[classof(it)];
};


/***/ }),

/***/ "./node_modules/core-js/internals/global.js":
/*!**************************************************!*\
  !*** ./node_modules/core-js/internals/global.js ***!
  \**************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

// https://github.com/zloirock/core-js/issues/86#issuecomment-115759028
module.exports = typeof window == 'object' && window && window.Math == Math ? window
  : typeof self == 'object' && self && self.Math == Math ? self
  // eslint-disable-next-line no-new-func
  : Function('return this')();


/***/ }),

/***/ "./node_modules/core-js/internals/has.js":
/*!***********************************************!*\
  !*** ./node_modules/core-js/internals/has.js ***!
  \***********************************************/
/*! no static exports found */
/***/ (function(module, exports) {

var hasOwnProperty = {}.hasOwnProperty;

module.exports = function (it, key) {
  return hasOwnProperty.call(it, key);
};


/***/ }),

/***/ "./node_modules/core-js/internals/hidden-keys.js":
/*!*******************************************************!*\
  !*** ./node_modules/core-js/internals/hidden-keys.js ***!
  \*******************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = {};


/***/ }),

/***/ "./node_modules/core-js/internals/hide.js":
/*!************************************************!*\
  !*** ./node_modules/core-js/internals/hide.js ***!
  \************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var definePropertyModule = __webpack_require__(/*! ../internals/object-define-property */ "./node_modules/core-js/internals/object-define-property.js");
var createPropertyDescriptor = __webpack_require__(/*! ../internals/create-property-descriptor */ "./node_modules/core-js/internals/create-property-descriptor.js");

module.exports = __webpack_require__(/*! ../internals/descriptors */ "./node_modules/core-js/internals/descriptors.js") ? function (object, key, value) {
  return definePropertyModule.f(object, key, createPropertyDescriptor(1, value));
} : function (object, key, value) {
  object[key] = value;
  return object;
};


/***/ }),

/***/ "./node_modules/core-js/internals/host-report-errors.js":
/*!**************************************************************!*\
  !*** ./node_modules/core-js/internals/host-report-errors.js ***!
  \**************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(/*! ../internals/global */ "./node_modules/core-js/internals/global.js");

module.exports = function (a, b) {
  var console = global.console;
  if (console && console.error) {
    arguments.length === 1 ? console.error(a) : console.error(a, b);
  }
};


/***/ }),

/***/ "./node_modules/core-js/internals/html.js":
/*!************************************************!*\
  !*** ./node_modules/core-js/internals/html.js ***!
  \************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var document = __webpack_require__(/*! ../internals/global */ "./node_modules/core-js/internals/global.js").document;

module.exports = document && document.documentElement;


/***/ }),

/***/ "./node_modules/core-js/internals/ie8-dom-define.js":
/*!**********************************************************!*\
  !*** ./node_modules/core-js/internals/ie8-dom-define.js ***!
  \**********************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

// Thank's IE8 for his funny defineProperty
module.exports = !__webpack_require__(/*! ../internals/descriptors */ "./node_modules/core-js/internals/descriptors.js") && !__webpack_require__(/*! ../internals/fails */ "./node_modules/core-js/internals/fails.js")(function () {
  return Object.defineProperty(__webpack_require__(/*! ../internals/document-create-element */ "./node_modules/core-js/internals/document-create-element.js")('div'), 'a', {
    get: function () { return 7; }
  }).a != 7;
});


/***/ }),

/***/ "./node_modules/core-js/internals/indexed-object.js":
/*!**********************************************************!*\
  !*** ./node_modules/core-js/internals/indexed-object.js ***!
  \**********************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

// fallback for non-array-like ES3 and non-enumerable old V8 strings
var fails = __webpack_require__(/*! ../internals/fails */ "./node_modules/core-js/internals/fails.js");
var classof = __webpack_require__(/*! ../internals/classof-raw */ "./node_modules/core-js/internals/classof-raw.js");
var split = ''.split;

module.exports = fails(function () {
  // throws an error in rhino, see https://github.com/mozilla/rhino/issues/346
  // eslint-disable-next-line no-prototype-builtins
  return !Object('z').propertyIsEnumerable(0);
}) ? function (it) {
  return classof(it) == 'String' ? split.call(it, '') : Object(it);
} : Object;


/***/ }),

/***/ "./node_modules/core-js/internals/internal-state.js":
/*!**********************************************************!*\
  !*** ./node_modules/core-js/internals/internal-state.js ***!
  \**********************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var NATIVE_WEAK_MAP = __webpack_require__(/*! ../internals/native-weak-map */ "./node_modules/core-js/internals/native-weak-map.js");
var isObject = __webpack_require__(/*! ../internals/is-object */ "./node_modules/core-js/internals/is-object.js");
var hide = __webpack_require__(/*! ../internals/hide */ "./node_modules/core-js/internals/hide.js");
var objectHas = __webpack_require__(/*! ../internals/has */ "./node_modules/core-js/internals/has.js");
var sharedKey = __webpack_require__(/*! ../internals/shared-key */ "./node_modules/core-js/internals/shared-key.js");
var hiddenKeys = __webpack_require__(/*! ../internals/hidden-keys */ "./node_modules/core-js/internals/hidden-keys.js");
var WeakMap = __webpack_require__(/*! ../internals/global */ "./node_modules/core-js/internals/global.js").WeakMap;
var set, get, has;

var enforce = function (it) {
  return has(it) ? get(it) : set(it, {});
};

var getterFor = function (TYPE) {
  return function (it) {
    var state;
    if (!isObject(it) || (state = get(it)).type !== TYPE) {
      throw TypeError('Incompatible receiver, ' + TYPE + ' required');
    } return state;
  };
};

if (NATIVE_WEAK_MAP) {
  var store = new WeakMap();
  var wmget = store.get;
  var wmhas = store.has;
  var wmset = store.set;
  set = function (it, metadata) {
    wmset.call(store, it, metadata);
    return metadata;
  };
  get = function (it) {
    return wmget.call(store, it) || {};
  };
  has = function (it) {
    return wmhas.call(store, it);
  };
} else {
  var STATE = sharedKey('state');
  hiddenKeys[STATE] = true;
  set = function (it, metadata) {
    hide(it, STATE, metadata);
    return metadata;
  };
  get = function (it) {
    return objectHas(it, STATE) ? it[STATE] : {};
  };
  has = function (it) {
    return objectHas(it, STATE);
  };
}

module.exports = {
  set: set,
  get: get,
  has: has,
  enforce: enforce,
  getterFor: getterFor
};


/***/ }),

/***/ "./node_modules/core-js/internals/is-array-iterator-method.js":
/*!********************************************************************!*\
  !*** ./node_modules/core-js/internals/is-array-iterator-method.js ***!
  \********************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

// check on default Array iterator
var Iterators = __webpack_require__(/*! ../internals/iterators */ "./node_modules/core-js/internals/iterators.js");
var ITERATOR = __webpack_require__(/*! ../internals/well-known-symbol */ "./node_modules/core-js/internals/well-known-symbol.js")('iterator');
var ArrayPrototype = Array.prototype;

module.exports = function (it) {
  return it !== undefined && (Iterators.Array === it || ArrayPrototype[ITERATOR] === it);
};


/***/ }),

/***/ "./node_modules/core-js/internals/is-array.js":
/*!****************************************************!*\
  !*** ./node_modules/core-js/internals/is-array.js ***!
  \****************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var classof = __webpack_require__(/*! ../internals/classof-raw */ "./node_modules/core-js/internals/classof-raw.js");

// `IsArray` abstract operation
// https://tc39.github.io/ecma262/#sec-isarray
module.exports = Array.isArray || function isArray(arg) {
  return classof(arg) == 'Array';
};


/***/ }),

/***/ "./node_modules/core-js/internals/is-forced.js":
/*!*****************************************************!*\
  !*** ./node_modules/core-js/internals/is-forced.js ***!
  \*****************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var fails = __webpack_require__(/*! ../internals/fails */ "./node_modules/core-js/internals/fails.js");
var replacement = /#|\.prototype\./;

var isForced = function (feature, detection) {
  var value = data[normalize(feature)];
  return value == POLYFILL ? true
    : value == NATIVE ? false
    : typeof detection == 'function' ? fails(detection)
    : !!detection;
};

var normalize = isForced.normalize = function (string) {
  return String(string).replace(replacement, '.').toLowerCase();
};

var data = isForced.data = {};
var NATIVE = isForced.NATIVE = 'N';
var POLYFILL = isForced.POLYFILL = 'P';

module.exports = isForced;


/***/ }),

/***/ "./node_modules/core-js/internals/is-object.js":
/*!*****************************************************!*\
  !*** ./node_modules/core-js/internals/is-object.js ***!
  \*****************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = function (it) {
  return typeof it === 'object' ? it !== null : typeof it === 'function';
};


/***/ }),

/***/ "./node_modules/core-js/internals/is-pure.js":
/*!***************************************************!*\
  !*** ./node_modules/core-js/internals/is-pure.js ***!
  \***************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = false;


/***/ }),

/***/ "./node_modules/core-js/internals/iterate.js":
/*!***************************************************!*\
  !*** ./node_modules/core-js/internals/iterate.js ***!
  \***************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var anObject = __webpack_require__(/*! ../internals/an-object */ "./node_modules/core-js/internals/an-object.js");
var isArrayIteratorMethod = __webpack_require__(/*! ../internals/is-array-iterator-method */ "./node_modules/core-js/internals/is-array-iterator-method.js");
var toLength = __webpack_require__(/*! ../internals/to-length */ "./node_modules/core-js/internals/to-length.js");
var bind = __webpack_require__(/*! ../internals/bind-context */ "./node_modules/core-js/internals/bind-context.js");
var getIteratorMethod = __webpack_require__(/*! ../internals/get-iterator-method */ "./node_modules/core-js/internals/get-iterator-method.js");
var callWithSafeIterationClosing = __webpack_require__(/*! ../internals/call-with-safe-iteration-closing */ "./node_modules/core-js/internals/call-with-safe-iteration-closing.js");
var BREAK = {};

var exports = module.exports = function (iterable, fn, that, ENTRIES, ITERATOR) {
  var boundFunction = bind(fn, that, ENTRIES ? 2 : 1);
  var iterator, iterFn, index, length, result, step;

  if (ITERATOR) {
    iterator = iterable;
  } else {
    iterFn = getIteratorMethod(iterable);
    if (typeof iterFn != 'function') throw TypeError('Target is not iterable');
    // optimisation for array iterators
    if (isArrayIteratorMethod(iterFn)) {
      for (index = 0, length = toLength(iterable.length); length > index; index++) {
        result = ENTRIES ? boundFunction(anObject(step = iterable[index])[0], step[1]) : boundFunction(iterable[index]);
        if (result === BREAK) return BREAK;
      } return;
    }
    iterator = iterFn.call(iterable);
  }

  while (!(step = iterator.next()).done) {
    if (callWithSafeIterationClosing(iterator, boundFunction, step.value, ENTRIES) === BREAK) return BREAK;
  }
};

exports.BREAK = BREAK;


/***/ }),

/***/ "./node_modules/core-js/internals/iterators-core.js":
/*!**********************************************************!*\
  !*** ./node_modules/core-js/internals/iterators-core.js ***!
  \**********************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var getPrototypeOf = __webpack_require__(/*! ../internals/object-get-prototype-of */ "./node_modules/core-js/internals/object-get-prototype-of.js");
var hide = __webpack_require__(/*! ../internals/hide */ "./node_modules/core-js/internals/hide.js");
var has = __webpack_require__(/*! ../internals/has */ "./node_modules/core-js/internals/has.js");
var IS_PURE = __webpack_require__(/*! ../internals/is-pure */ "./node_modules/core-js/internals/is-pure.js");
var ITERATOR = __webpack_require__(/*! ../internals/well-known-symbol */ "./node_modules/core-js/internals/well-known-symbol.js")('iterator');
var BUGGY_SAFARI_ITERATORS = false;

var returnThis = function () { return this; };

// `%IteratorPrototype%` object
// https://tc39.github.io/ecma262/#sec-%iteratorprototype%-object
var IteratorPrototype, PrototypeOfArrayIteratorPrototype, arrayIterator;

if ([].keys) {
  arrayIterator = [].keys();
  // Safari 8 has buggy iterators w/o `next`
  if (!('next' in arrayIterator)) BUGGY_SAFARI_ITERATORS = true;
  else {
    PrototypeOfArrayIteratorPrototype = getPrototypeOf(getPrototypeOf(arrayIterator));
    if (PrototypeOfArrayIteratorPrototype !== Object.prototype) IteratorPrototype = PrototypeOfArrayIteratorPrototype;
  }
}

if (IteratorPrototype == undefined) IteratorPrototype = {};

// 25.1.2.1.1 %IteratorPrototype%[@@iterator]()
if (!IS_PURE && !has(IteratorPrototype, ITERATOR)) hide(IteratorPrototype, ITERATOR, returnThis);

module.exports = {
  IteratorPrototype: IteratorPrototype,
  BUGGY_SAFARI_ITERATORS: BUGGY_SAFARI_ITERATORS
};


/***/ }),

/***/ "./node_modules/core-js/internals/iterators.js":
/*!*****************************************************!*\
  !*** ./node_modules/core-js/internals/iterators.js ***!
  \*****************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = {};


/***/ }),

/***/ "./node_modules/core-js/internals/microtask.js":
/*!*****************************************************!*\
  !*** ./node_modules/core-js/internals/microtask.js ***!
  \*****************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(/*! ../internals/global */ "./node_modules/core-js/internals/global.js");
var getOwnPropertyDescriptor = __webpack_require__(/*! ../internals/object-get-own-property-descriptor */ "./node_modules/core-js/internals/object-get-own-property-descriptor.js").f;
var classof = __webpack_require__(/*! ../internals/classof-raw */ "./node_modules/core-js/internals/classof-raw.js");
var macrotask = __webpack_require__(/*! ../internals/task */ "./node_modules/core-js/internals/task.js").set;
var userAgent = __webpack_require__(/*! ../internals/user-agent */ "./node_modules/core-js/internals/user-agent.js");
var MutationObserver = global.MutationObserver || global.WebKitMutationObserver;
var process = global.process;
var Promise = global.Promise;
var IS_NODE = classof(process) == 'process';
// Node.js 11 shows ExperimentalWarning on getting `queueMicrotask`
var queueMicrotaskDescriptor = getOwnPropertyDescriptor(global, 'queueMicrotask');
var queueMicrotask = queueMicrotaskDescriptor && queueMicrotaskDescriptor.value;

var flush, head, last, notify, toggle, node, promise;

// modern engines have queueMicrotask method
if (!queueMicrotask) {
  flush = function () {
    var parent, fn;
    if (IS_NODE && (parent = process.domain)) parent.exit();
    while (head) {
      fn = head.fn;
      head = head.next;
      try {
        fn();
      } catch (error) {
        if (head) notify();
        else last = undefined;
        throw error;
      }
    } last = undefined;
    if (parent) parent.enter();
  };

  // Node.js
  if (IS_NODE) {
    notify = function () {
      process.nextTick(flush);
    };
  // browsers with MutationObserver, except iOS - https://github.com/zloirock/core-js/issues/339
  } else if (MutationObserver && !/(iPhone|iPod|iPad).*AppleWebKit/i.test(userAgent)) {
    toggle = true;
    node = document.createTextNode('');
    new MutationObserver(flush).observe(node, { characterData: true }); // eslint-disable-line no-new
    notify = function () {
      node.data = toggle = !toggle;
    };
  // environments with maybe non-completely correct, but existent Promise
  } else if (Promise && Promise.resolve) {
    // Promise.resolve without an argument throws an error in LG WebOS 2
    promise = Promise.resolve(undefined);
    notify = function () {
      promise.then(flush);
    };
  // for other environments - macrotask based on:
  // - setImmediate
  // - MessageChannel
  // - window.postMessag
  // - onreadystatechange
  // - setTimeout
  } else {
    notify = function () {
      // strange IE + webpack dev server bug - use .call(global)
      macrotask.call(global, flush);
    };
  }
}

module.exports = queueMicrotask || function (fn) {
  var task = { fn: fn, next: undefined };
  if (last) last.next = task;
  if (!head) {
    head = task;
    notify();
  } last = task;
};


/***/ }),

/***/ "./node_modules/core-js/internals/native-symbol.js":
/*!*********************************************************!*\
  !*** ./node_modules/core-js/internals/native-symbol.js ***!
  \*********************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

// Chrome 38 Symbol has incorrect toString conversion
module.exports = !__webpack_require__(/*! ../internals/fails */ "./node_modules/core-js/internals/fails.js")(function () {
  // eslint-disable-next-line no-undef
  return !String(Symbol());
});


/***/ }),

/***/ "./node_modules/core-js/internals/native-weak-map.js":
/*!***********************************************************!*\
  !*** ./node_modules/core-js/internals/native-weak-map.js ***!
  \***********************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var nativeFunctionToString = __webpack_require__(/*! ../internals/function-to-string */ "./node_modules/core-js/internals/function-to-string.js");
var WeakMap = __webpack_require__(/*! ../internals/global */ "./node_modules/core-js/internals/global.js").WeakMap;

module.exports = typeof WeakMap === 'function' && /native code/.test(nativeFunctionToString.call(WeakMap));


/***/ }),

/***/ "./node_modules/core-js/internals/new-promise-capability.js":
/*!******************************************************************!*\
  !*** ./node_modules/core-js/internals/new-promise-capability.js ***!
  \******************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

// 25.4.1.5 NewPromiseCapability(C)
var aFunction = __webpack_require__(/*! ../internals/a-function */ "./node_modules/core-js/internals/a-function.js");

var PromiseCapability = function (C) {
  var resolve, reject;
  this.promise = new C(function ($$resolve, $$reject) {
    if (resolve !== undefined || reject !== undefined) throw TypeError('Bad Promise constructor');
    resolve = $$resolve;
    reject = $$reject;
  });
  this.resolve = aFunction(resolve);
  this.reject = aFunction(reject);
};

module.exports.f = function (C) {
  return new PromiseCapability(C);
};


/***/ }),

/***/ "./node_modules/core-js/internals/object-assign.js":
/*!*********************************************************!*\
  !*** ./node_modules/core-js/internals/object-assign.js ***!
  \*********************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

// 19.1.2.1 Object.assign(target, source, ...)
var objectKeys = __webpack_require__(/*! ../internals/object-keys */ "./node_modules/core-js/internals/object-keys.js");
var getOwnPropertySymbolsModule = __webpack_require__(/*! ../internals/object-get-own-property-symbols */ "./node_modules/core-js/internals/object-get-own-property-symbols.js");
var propertyIsEnumerableModule = __webpack_require__(/*! ../internals/object-property-is-enumerable */ "./node_modules/core-js/internals/object-property-is-enumerable.js");
var toObject = __webpack_require__(/*! ../internals/to-object */ "./node_modules/core-js/internals/to-object.js");
var IndexedObject = __webpack_require__(/*! ../internals/indexed-object */ "./node_modules/core-js/internals/indexed-object.js");
var nativeAssign = Object.assign;

// should work with symbols and should have deterministic property order (V8 bug)
module.exports = !nativeAssign || __webpack_require__(/*! ../internals/fails */ "./node_modules/core-js/internals/fails.js")(function () {
  var A = {};
  var B = {};
  // eslint-disable-next-line no-undef
  var symbol = Symbol();
  var alphabet = 'abcdefghijklmnopqrst';
  A[symbol] = 7;
  alphabet.split('').forEach(function (chr) { B[chr] = chr; });
  return nativeAssign({}, A)[symbol] != 7 || objectKeys(nativeAssign({}, B)).join('') != alphabet;
}) ? function assign(target, source) { // eslint-disable-line no-unused-vars
  var T = toObject(target);
  var argumentsLength = arguments.length;
  var index = 1;
  var getOwnPropertySymbols = getOwnPropertySymbolsModule.f;
  var propertyIsEnumerable = propertyIsEnumerableModule.f;
  while (argumentsLength > index) {
    var S = IndexedObject(arguments[index++]);
    var keys = getOwnPropertySymbols ? objectKeys(S).concat(getOwnPropertySymbols(S)) : objectKeys(S);
    var length = keys.length;
    var j = 0;
    var key;
    while (length > j) if (propertyIsEnumerable.call(S, key = keys[j++])) T[key] = S[key];
  } return T;
} : nativeAssign;


/***/ }),

/***/ "./node_modules/core-js/internals/object-create.js":
/*!*********************************************************!*\
  !*** ./node_modules/core-js/internals/object-create.js ***!
  \*********************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

// 19.1.2.2 / 15.2.3.5 Object.create(O [, Properties])
var anObject = __webpack_require__(/*! ../internals/an-object */ "./node_modules/core-js/internals/an-object.js");
var defineProperties = __webpack_require__(/*! ../internals/object-define-properties */ "./node_modules/core-js/internals/object-define-properties.js");
var enumBugKeys = __webpack_require__(/*! ../internals/enum-bug-keys */ "./node_modules/core-js/internals/enum-bug-keys.js");
var html = __webpack_require__(/*! ../internals/html */ "./node_modules/core-js/internals/html.js");
var documentCreateElement = __webpack_require__(/*! ../internals/document-create-element */ "./node_modules/core-js/internals/document-create-element.js");
var IE_PROTO = __webpack_require__(/*! ../internals/shared-key */ "./node_modules/core-js/internals/shared-key.js")('IE_PROTO');
var PROTOTYPE = 'prototype';
var Empty = function () { /* empty */ };

// Create object with fake `null` prototype: use iframe Object with cleared prototype
var createDict = function () {
  // Thrash, waste and sodomy: IE GC bug
  var iframe = documentCreateElement('iframe');
  var length = enumBugKeys.length;
  var lt = '<';
  var script = 'script';
  var gt = '>';
  var js = 'java' + script + ':';
  var iframeDocument;
  iframe.style.display = 'none';
  html.appendChild(iframe);
  iframe.src = String(js);
  iframeDocument = iframe.contentWindow.document;
  iframeDocument.open();
  iframeDocument.write(lt + script + gt + 'document.F=Object' + lt + '/' + script + gt);
  iframeDocument.close();
  createDict = iframeDocument.F;
  while (length--) delete createDict[PROTOTYPE][enumBugKeys[length]];
  return createDict();
};

module.exports = Object.create || function create(O, Properties) {
  var result;
  if (O !== null) {
    Empty[PROTOTYPE] = anObject(O);
    result = new Empty();
    Empty[PROTOTYPE] = null;
    // add "__proto__" for Object.getPrototypeOf polyfill
    result[IE_PROTO] = O;
  } else result = createDict();
  return Properties === undefined ? result : defineProperties(result, Properties);
};

__webpack_require__(/*! ../internals/hidden-keys */ "./node_modules/core-js/internals/hidden-keys.js")[IE_PROTO] = true;


/***/ }),

/***/ "./node_modules/core-js/internals/object-define-properties.js":
/*!********************************************************************!*\
  !*** ./node_modules/core-js/internals/object-define-properties.js ***!
  \********************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var DESCRIPTORS = __webpack_require__(/*! ../internals/descriptors */ "./node_modules/core-js/internals/descriptors.js");
var definePropertyModule = __webpack_require__(/*! ../internals/object-define-property */ "./node_modules/core-js/internals/object-define-property.js");
var anObject = __webpack_require__(/*! ../internals/an-object */ "./node_modules/core-js/internals/an-object.js");
var objectKeys = __webpack_require__(/*! ../internals/object-keys */ "./node_modules/core-js/internals/object-keys.js");

module.exports = DESCRIPTORS ? Object.defineProperties : function defineProperties(O, Properties) {
  anObject(O);
  var keys = objectKeys(Properties);
  var length = keys.length;
  var i = 0;
  var key;
  while (length > i) definePropertyModule.f(O, key = keys[i++], Properties[key]);
  return O;
};


/***/ }),

/***/ "./node_modules/core-js/internals/object-define-property.js":
/*!******************************************************************!*\
  !*** ./node_modules/core-js/internals/object-define-property.js ***!
  \******************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var DESCRIPTORS = __webpack_require__(/*! ../internals/descriptors */ "./node_modules/core-js/internals/descriptors.js");
var IE8_DOM_DEFINE = __webpack_require__(/*! ../internals/ie8-dom-define */ "./node_modules/core-js/internals/ie8-dom-define.js");
var anObject = __webpack_require__(/*! ../internals/an-object */ "./node_modules/core-js/internals/an-object.js");
var toPrimitive = __webpack_require__(/*! ../internals/to-primitive */ "./node_modules/core-js/internals/to-primitive.js");
var nativeDefineProperty = Object.defineProperty;

exports.f = DESCRIPTORS ? nativeDefineProperty : function defineProperty(O, P, Attributes) {
  anObject(O);
  P = toPrimitive(P, true);
  anObject(Attributes);
  if (IE8_DOM_DEFINE) try {
    return nativeDefineProperty(O, P, Attributes);
  } catch (error) { /* empty */ }
  if ('get' in Attributes || 'set' in Attributes) throw TypeError('Accessors not supported');
  if ('value' in Attributes) O[P] = Attributes.value;
  return O;
};


/***/ }),

/***/ "./node_modules/core-js/internals/object-get-own-property-descriptor.js":
/*!******************************************************************************!*\
  !*** ./node_modules/core-js/internals/object-get-own-property-descriptor.js ***!
  \******************************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var DESCRIPTORS = __webpack_require__(/*! ../internals/descriptors */ "./node_modules/core-js/internals/descriptors.js");
var propertyIsEnumerableModule = __webpack_require__(/*! ../internals/object-property-is-enumerable */ "./node_modules/core-js/internals/object-property-is-enumerable.js");
var createPropertyDescriptor = __webpack_require__(/*! ../internals/create-property-descriptor */ "./node_modules/core-js/internals/create-property-descriptor.js");
var toIndexedObject = __webpack_require__(/*! ../internals/to-indexed-object */ "./node_modules/core-js/internals/to-indexed-object.js");
var toPrimitive = __webpack_require__(/*! ../internals/to-primitive */ "./node_modules/core-js/internals/to-primitive.js");
var has = __webpack_require__(/*! ../internals/has */ "./node_modules/core-js/internals/has.js");
var IE8_DOM_DEFINE = __webpack_require__(/*! ../internals/ie8-dom-define */ "./node_modules/core-js/internals/ie8-dom-define.js");
var nativeGetOwnPropertyDescriptor = Object.getOwnPropertyDescriptor;

exports.f = DESCRIPTORS ? nativeGetOwnPropertyDescriptor : function getOwnPropertyDescriptor(O, P) {
  O = toIndexedObject(O);
  P = toPrimitive(P, true);
  if (IE8_DOM_DEFINE) try {
    return nativeGetOwnPropertyDescriptor(O, P);
  } catch (error) { /* empty */ }
  if (has(O, P)) return createPropertyDescriptor(!propertyIsEnumerableModule.f.call(O, P), O[P]);
};


/***/ }),

/***/ "./node_modules/core-js/internals/object-get-own-property-names.js":
/*!*************************************************************************!*\
  !*** ./node_modules/core-js/internals/object-get-own-property-names.js ***!
  \*************************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

// 19.1.2.7 / 15.2.3.4 Object.getOwnPropertyNames(O)
var internalObjectKeys = __webpack_require__(/*! ../internals/object-keys-internal */ "./node_modules/core-js/internals/object-keys-internal.js");
var hiddenKeys = __webpack_require__(/*! ../internals/enum-bug-keys */ "./node_modules/core-js/internals/enum-bug-keys.js").concat('length', 'prototype');

exports.f = Object.getOwnPropertyNames || function getOwnPropertyNames(O) {
  return internalObjectKeys(O, hiddenKeys);
};


/***/ }),

/***/ "./node_modules/core-js/internals/object-get-own-property-symbols.js":
/*!***************************************************************************!*\
  !*** ./node_modules/core-js/internals/object-get-own-property-symbols.js ***!
  \***************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

exports.f = Object.getOwnPropertySymbols;


/***/ }),

/***/ "./node_modules/core-js/internals/object-get-prototype-of.js":
/*!*******************************************************************!*\
  !*** ./node_modules/core-js/internals/object-get-prototype-of.js ***!
  \*******************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

// 19.1.2.9 / 15.2.3.2 Object.getPrototypeOf(O)
var has = __webpack_require__(/*! ../internals/has */ "./node_modules/core-js/internals/has.js");
var toObject = __webpack_require__(/*! ../internals/to-object */ "./node_modules/core-js/internals/to-object.js");
var IE_PROTO = __webpack_require__(/*! ../internals/shared-key */ "./node_modules/core-js/internals/shared-key.js")('IE_PROTO');
var CORRECT_PROTOTYPE_GETTER = __webpack_require__(/*! ../internals/correct-prototype-getter */ "./node_modules/core-js/internals/correct-prototype-getter.js");
var ObjectPrototype = Object.prototype;

module.exports = CORRECT_PROTOTYPE_GETTER ? Object.getPrototypeOf : function (O) {
  O = toObject(O);
  if (has(O, IE_PROTO)) return O[IE_PROTO];
  if (typeof O.constructor == 'function' && O instanceof O.constructor) {
    return O.constructor.prototype;
  } return O instanceof Object ? ObjectPrototype : null;
};


/***/ }),

/***/ "./node_modules/core-js/internals/object-keys-internal.js":
/*!****************************************************************!*\
  !*** ./node_modules/core-js/internals/object-keys-internal.js ***!
  \****************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var has = __webpack_require__(/*! ../internals/has */ "./node_modules/core-js/internals/has.js");
var toIndexedObject = __webpack_require__(/*! ../internals/to-indexed-object */ "./node_modules/core-js/internals/to-indexed-object.js");
var arrayIndexOf = __webpack_require__(/*! ../internals/array-includes */ "./node_modules/core-js/internals/array-includes.js")(false);
var hiddenKeys = __webpack_require__(/*! ../internals/hidden-keys */ "./node_modules/core-js/internals/hidden-keys.js");

module.exports = function (object, names) {
  var O = toIndexedObject(object);
  var i = 0;
  var result = [];
  var key;
  for (key in O) !has(hiddenKeys, key) && has(O, key) && result.push(key);
  // Don't enum bug & hidden keys
  while (names.length > i) if (has(O, key = names[i++])) {
    ~arrayIndexOf(result, key) || result.push(key);
  }
  return result;
};


/***/ }),

/***/ "./node_modules/core-js/internals/object-keys.js":
/*!*******************************************************!*\
  !*** ./node_modules/core-js/internals/object-keys.js ***!
  \*******************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

// 19.1.2.14 / 15.2.3.14 Object.keys(O)
var internalObjectKeys = __webpack_require__(/*! ../internals/object-keys-internal */ "./node_modules/core-js/internals/object-keys-internal.js");
var enumBugKeys = __webpack_require__(/*! ../internals/enum-bug-keys */ "./node_modules/core-js/internals/enum-bug-keys.js");

module.exports = Object.keys || function keys(O) {
  return internalObjectKeys(O, enumBugKeys);
};


/***/ }),

/***/ "./node_modules/core-js/internals/object-property-is-enumerable.js":
/*!*************************************************************************!*\
  !*** ./node_modules/core-js/internals/object-property-is-enumerable.js ***!
  \*************************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var nativePropertyIsEnumerable = {}.propertyIsEnumerable;
var nativeGetOwnPropertyDescriptor = Object.getOwnPropertyDescriptor;

// Nashorn ~ JDK8 bug
var NASHORN_BUG = nativeGetOwnPropertyDescriptor && !nativePropertyIsEnumerable.call({ 1: 2 }, 1);

exports.f = NASHORN_BUG ? function propertyIsEnumerable(V) {
  var descriptor = nativeGetOwnPropertyDescriptor(this, V);
  return !!descriptor && descriptor.enumerable;
} : nativePropertyIsEnumerable;


/***/ }),

/***/ "./node_modules/core-js/internals/object-set-prototype-of.js":
/*!*******************************************************************!*\
  !*** ./node_modules/core-js/internals/object-set-prototype-of.js ***!
  \*******************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

// Works with __proto__ only. Old v8 can't work with null proto objects.
/* eslint-disable no-proto */
var validateSetPrototypeOfArguments = __webpack_require__(/*! ../internals/validate-set-prototype-of-arguments */ "./node_modules/core-js/internals/validate-set-prototype-of-arguments.js");

module.exports = Object.setPrototypeOf || ('__proto__' in {} ? function () {
  var correctSetter = false;
  var test = {};
  var setter;
  try {
    setter = Object.getOwnPropertyDescriptor(Object.prototype, '__proto__').set;
    setter.call(test, []);
    correctSetter = test instanceof Array;
  } catch (error) { /* empty */ }
  return function setPrototypeOf(O, proto) {
    validateSetPrototypeOfArguments(O, proto);
    if (correctSetter) setter.call(O, proto);
    else O.__proto__ = proto;
    return O;
  };
}() : undefined);


/***/ }),

/***/ "./node_modules/core-js/internals/object-to-string.js":
/*!************************************************************!*\
  !*** ./node_modules/core-js/internals/object-to-string.js ***!
  \************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var classof = __webpack_require__(/*! ../internals/classof */ "./node_modules/core-js/internals/classof.js");
var TO_STRING_TAG = __webpack_require__(/*! ../internals/well-known-symbol */ "./node_modules/core-js/internals/well-known-symbol.js")('toStringTag');
var test = {};

test[TO_STRING_TAG] = 'z';

// `Object.prototype.toString` method implementation
// https://tc39.github.io/ecma262/#sec-object.prototype.tostring
module.exports = String(test) !== '[object z]' ? function toString() {
  return '[object ' + classof(this) + ']';
} : test.toString;


/***/ }),

/***/ "./node_modules/core-js/internals/own-keys.js":
/*!****************************************************!*\
  !*** ./node_modules/core-js/internals/own-keys.js ***!
  \****************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var getOwnPropertyNamesModule = __webpack_require__(/*! ../internals/object-get-own-property-names */ "./node_modules/core-js/internals/object-get-own-property-names.js");
var getOwnPropertySymbolsModule = __webpack_require__(/*! ../internals/object-get-own-property-symbols */ "./node_modules/core-js/internals/object-get-own-property-symbols.js");
var anObject = __webpack_require__(/*! ../internals/an-object */ "./node_modules/core-js/internals/an-object.js");
var Reflect = __webpack_require__(/*! ../internals/global */ "./node_modules/core-js/internals/global.js").Reflect;

// all object keys, includes non-enumerable and symbols
module.exports = Reflect && Reflect.ownKeys || function ownKeys(it) {
  var keys = getOwnPropertyNamesModule.f(anObject(it));
  var getOwnPropertySymbols = getOwnPropertySymbolsModule.f;
  return getOwnPropertySymbols ? keys.concat(getOwnPropertySymbols(it)) : keys;
};


/***/ }),

/***/ "./node_modules/core-js/internals/path.js":
/*!************************************************!*\
  !*** ./node_modules/core-js/internals/path.js ***!
  \************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__(/*! ../internals/global */ "./node_modules/core-js/internals/global.js");


/***/ }),

/***/ "./node_modules/core-js/internals/perform.js":
/*!***************************************************!*\
  !*** ./node_modules/core-js/internals/perform.js ***!
  \***************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = function (exec) {
  try {
    return { error: false, value: exec() };
  } catch (error) {
    return { error: true, value: error };
  }
};


/***/ }),

/***/ "./node_modules/core-js/internals/promise-resolve.js":
/*!***********************************************************!*\
  !*** ./node_modules/core-js/internals/promise-resolve.js ***!
  \***********************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var anObject = __webpack_require__(/*! ../internals/an-object */ "./node_modules/core-js/internals/an-object.js");
var isObject = __webpack_require__(/*! ../internals/is-object */ "./node_modules/core-js/internals/is-object.js");
var newPromiseCapability = __webpack_require__(/*! ../internals/new-promise-capability */ "./node_modules/core-js/internals/new-promise-capability.js");

module.exports = function (C, x) {
  anObject(C);
  if (isObject(x) && x.constructor === C) return x;
  var promiseCapability = newPromiseCapability.f(C);
  var resolve = promiseCapability.resolve;
  resolve(x);
  return promiseCapability.promise;
};


/***/ }),

/***/ "./node_modules/core-js/internals/redefine-all.js":
/*!********************************************************!*\
  !*** ./node_modules/core-js/internals/redefine-all.js ***!
  \********************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var redefine = __webpack_require__(/*! ../internals/redefine */ "./node_modules/core-js/internals/redefine.js");

module.exports = function (target, src, options) {
  for (var key in src) redefine(target, key, src[key], options);
  return target;
};


/***/ }),

/***/ "./node_modules/core-js/internals/redefine.js":
/*!****************************************************!*\
  !*** ./node_modules/core-js/internals/redefine.js ***!
  \****************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(/*! ../internals/global */ "./node_modules/core-js/internals/global.js");
var hide = __webpack_require__(/*! ../internals/hide */ "./node_modules/core-js/internals/hide.js");
var has = __webpack_require__(/*! ../internals/has */ "./node_modules/core-js/internals/has.js");
var setGlobal = __webpack_require__(/*! ../internals/set-global */ "./node_modules/core-js/internals/set-global.js");
var nativeFunctionToString = __webpack_require__(/*! ../internals/function-to-string */ "./node_modules/core-js/internals/function-to-string.js");
var InternalStateModule = __webpack_require__(/*! ../internals/internal-state */ "./node_modules/core-js/internals/internal-state.js");
var getInternalState = InternalStateModule.get;
var enforceInternalState = InternalStateModule.enforce;
var TEMPLATE = String(nativeFunctionToString).split('toString');

__webpack_require__(/*! ../internals/shared */ "./node_modules/core-js/internals/shared.js")('inspectSource', function (it) {
  return nativeFunctionToString.call(it);
});

(module.exports = function (O, key, value, options) {
  var unsafe = options ? !!options.unsafe : false;
  var simple = options ? !!options.enumerable : false;
  var noTargetGet = options ? !!options.noTargetGet : false;
  if (typeof value == 'function') {
    if (typeof key == 'string' && !has(value, 'name')) hide(value, 'name', key);
    enforceInternalState(value).source = TEMPLATE.join(typeof key == 'string' ? key : '');
  }
  if (O === global) {
    if (simple) O[key] = value;
    else setGlobal(key, value);
    return;
  } else if (!unsafe) {
    delete O[key];
  } else if (!noTargetGet && O[key]) {
    simple = true;
  }
  if (simple) O[key] = value;
  else hide(O, key, value);
// add fake Function#toString for correct work wrapped methods / constructors with methods like LoDash isNative
})(Function.prototype, 'toString', function toString() {
  return typeof this == 'function' && getInternalState(this).source || nativeFunctionToString.call(this);
});


/***/ }),

/***/ "./node_modules/core-js/internals/require-object-coercible.js":
/*!********************************************************************!*\
  !*** ./node_modules/core-js/internals/require-object-coercible.js ***!
  \********************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

// `RequireObjectCoercible` abstract operation
// https://tc39.github.io/ecma262/#sec-requireobjectcoercible
module.exports = function (it) {
  if (it == undefined) throw TypeError("Can't call method on " + it);
  return it;
};


/***/ }),

/***/ "./node_modules/core-js/internals/set-global.js":
/*!******************************************************!*\
  !*** ./node_modules/core-js/internals/set-global.js ***!
  \******************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(/*! ../internals/global */ "./node_modules/core-js/internals/global.js");
var hide = __webpack_require__(/*! ../internals/hide */ "./node_modules/core-js/internals/hide.js");

module.exports = function (key, value) {
  try {
    hide(global, key, value);
  } catch (error) {
    global[key] = value;
  } return value;
};


/***/ }),

/***/ "./node_modules/core-js/internals/set-species.js":
/*!*******************************************************!*\
  !*** ./node_modules/core-js/internals/set-species.js ***!
  \*******************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var getBuiltIn = __webpack_require__(/*! ../internals/get-built-in */ "./node_modules/core-js/internals/get-built-in.js");
var definePropertyModule = __webpack_require__(/*! ../internals/object-define-property */ "./node_modules/core-js/internals/object-define-property.js");
var DESCRIPTORS = __webpack_require__(/*! ../internals/descriptors */ "./node_modules/core-js/internals/descriptors.js");
var SPECIES = __webpack_require__(/*! ../internals/well-known-symbol */ "./node_modules/core-js/internals/well-known-symbol.js")('species');

module.exports = function (CONSTRUCTOR_NAME) {
  var C = getBuiltIn(CONSTRUCTOR_NAME);
  var defineProperty = definePropertyModule.f;
  if (DESCRIPTORS && C && !C[SPECIES]) defineProperty(C, SPECIES, {
    configurable: true,
    get: function () { return this; }
  });
};


/***/ }),

/***/ "./node_modules/core-js/internals/set-to-string-tag.js":
/*!*************************************************************!*\
  !*** ./node_modules/core-js/internals/set-to-string-tag.js ***!
  \*************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var defineProperty = __webpack_require__(/*! ../internals/object-define-property */ "./node_modules/core-js/internals/object-define-property.js").f;
var has = __webpack_require__(/*! ../internals/has */ "./node_modules/core-js/internals/has.js");
var TO_STRING_TAG = __webpack_require__(/*! ../internals/well-known-symbol */ "./node_modules/core-js/internals/well-known-symbol.js")('toStringTag');

module.exports = function (it, TAG, STATIC) {
  if (it && !has(it = STATIC ? it : it.prototype, TO_STRING_TAG)) {
    defineProperty(it, TO_STRING_TAG, { configurable: true, value: TAG });
  }
};


/***/ }),

/***/ "./node_modules/core-js/internals/shared-key.js":
/*!******************************************************!*\
  !*** ./node_modules/core-js/internals/shared-key.js ***!
  \******************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var shared = __webpack_require__(/*! ../internals/shared */ "./node_modules/core-js/internals/shared.js")('keys');
var uid = __webpack_require__(/*! ../internals/uid */ "./node_modules/core-js/internals/uid.js");

module.exports = function (key) {
  return shared[key] || (shared[key] = uid(key));
};


/***/ }),

/***/ "./node_modules/core-js/internals/shared.js":
/*!**************************************************!*\
  !*** ./node_modules/core-js/internals/shared.js ***!
  \**************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(/*! ../internals/global */ "./node_modules/core-js/internals/global.js");
var setGlobal = __webpack_require__(/*! ../internals/set-global */ "./node_modules/core-js/internals/set-global.js");
var SHARED = '__core-js_shared__';
var store = global[SHARED] || setGlobal(SHARED, {});

(module.exports = function (key, value) {
  return store[key] || (store[key] = value !== undefined ? value : {});
})('versions', []).push({
  version: '3.0.1',
  mode: __webpack_require__(/*! ../internals/is-pure */ "./node_modules/core-js/internals/is-pure.js") ? 'pure' : 'global',
  copyright: '© 2019 Denis Pushkarev (zloirock.ru)'
});


/***/ }),

/***/ "./node_modules/core-js/internals/species-constructor.js":
/*!***************************************************************!*\
  !*** ./node_modules/core-js/internals/species-constructor.js ***!
  \***************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var anObject = __webpack_require__(/*! ../internals/an-object */ "./node_modules/core-js/internals/an-object.js");
var aFunction = __webpack_require__(/*! ../internals/a-function */ "./node_modules/core-js/internals/a-function.js");
var SPECIES = __webpack_require__(/*! ../internals/well-known-symbol */ "./node_modules/core-js/internals/well-known-symbol.js")('species');

// `SpeciesConstructor` abstract operation
// https://tc39.github.io/ecma262/#sec-speciesconstructor
module.exports = function (O, defaultConstructor) {
  var C = anObject(O).constructor;
  var S;
  return C === undefined || (S = anObject(C)[SPECIES]) == undefined ? defaultConstructor : aFunction(S);
};


/***/ }),

/***/ "./node_modules/core-js/internals/string-at.js":
/*!*****************************************************!*\
  !*** ./node_modules/core-js/internals/string-at.js ***!
  \*****************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var toInteger = __webpack_require__(/*! ../internals/to-integer */ "./node_modules/core-js/internals/to-integer.js");
var requireObjectCoercible = __webpack_require__(/*! ../internals/require-object-coercible */ "./node_modules/core-js/internals/require-object-coercible.js");
// CONVERT_TO_STRING: true  -> String#at
// CONVERT_TO_STRING: false -> String#codePointAt
module.exports = function (that, pos, CONVERT_TO_STRING) {
  var S = String(requireObjectCoercible(that));
  var position = toInteger(pos);
  var size = S.length;
  var first, second;
  if (position < 0 || position >= size) return CONVERT_TO_STRING ? '' : undefined;
  first = S.charCodeAt(position);
  return first < 0xD800 || first > 0xDBFF || position + 1 === size
    || (second = S.charCodeAt(position + 1)) < 0xDC00 || second > 0xDFFF
      ? CONVERT_TO_STRING ? S.charAt(position) : first
      : CONVERT_TO_STRING ? S.slice(position, position + 2) : (first - 0xD800 << 10) + (second - 0xDC00) + 0x10000;
};


/***/ }),

/***/ "./node_modules/core-js/internals/task.js":
/*!************************************************!*\
  !*** ./node_modules/core-js/internals/task.js ***!
  \************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(/*! ../internals/global */ "./node_modules/core-js/internals/global.js");
var classof = __webpack_require__(/*! ../internals/classof-raw */ "./node_modules/core-js/internals/classof-raw.js");
var bind = __webpack_require__(/*! ../internals/bind-context */ "./node_modules/core-js/internals/bind-context.js");
var html = __webpack_require__(/*! ../internals/html */ "./node_modules/core-js/internals/html.js");
var createElement = __webpack_require__(/*! ../internals/document-create-element */ "./node_modules/core-js/internals/document-create-element.js");
var set = global.setImmediate;
var clear = global.clearImmediate;
var process = global.process;
var MessageChannel = global.MessageChannel;
var Dispatch = global.Dispatch;
var counter = 0;
var queue = {};
var ONREADYSTATECHANGE = 'onreadystatechange';
var defer, channel, port;

var run = function () {
  var id = +this;
  // eslint-disable-next-line no-prototype-builtins
  if (queue.hasOwnProperty(id)) {
    var fn = queue[id];
    delete queue[id];
    fn();
  }
};

var listener = function (event) {
  run.call(event.data);
};

// Node.js 0.9+ & IE10+ has setImmediate, otherwise:
if (!set || !clear) {
  set = function setImmediate(fn) {
    var args = [];
    var i = 1;
    while (arguments.length > i) args.push(arguments[i++]);
    queue[++counter] = function () {
      // eslint-disable-next-line no-new-func
      (typeof fn == 'function' ? fn : Function(fn)).apply(undefined, args);
    };
    defer(counter);
    return counter;
  };
  clear = function clearImmediate(id) {
    delete queue[id];
  };
  // Node.js 0.8-
  if (classof(process) == 'process') {
    defer = function (id) {
      process.nextTick(bind(run, id, 1));
    };
  // Sphere (JS game engine) Dispatch API
  } else if (Dispatch && Dispatch.now) {
    defer = function (id) {
      Dispatch.now(bind(run, id, 1));
    };
  // Browsers with MessageChannel, includes WebWorkers
  } else if (MessageChannel) {
    channel = new MessageChannel();
    port = channel.port2;
    channel.port1.onmessage = listener;
    defer = bind(port.postMessage, port, 1);
  // Browsers with postMessage, skip WebWorkers
  // IE8 has postMessage, but it's sync & typeof its postMessage is 'object'
  } else if (global.addEventListener && typeof postMessage == 'function' && !global.importScripts) {
    defer = function (id) {
      global.postMessage(id + '', '*');
    };
    global.addEventListener('message', listener, false);
  // IE8-
  } else if (ONREADYSTATECHANGE in createElement('script')) {
    defer = function (id) {
      html.appendChild(createElement('script'))[ONREADYSTATECHANGE] = function () {
        html.removeChild(this);
        run.call(id);
      };
    };
  // Rest old browsers
  } else {
    defer = function (id) {
      setTimeout(bind(run, id, 1), 0);
    };
  }
}

module.exports = {
  set: set,
  clear: clear
};


/***/ }),

/***/ "./node_modules/core-js/internals/to-absolute-index.js":
/*!*************************************************************!*\
  !*** ./node_modules/core-js/internals/to-absolute-index.js ***!
  \*************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var toInteger = __webpack_require__(/*! ../internals/to-integer */ "./node_modules/core-js/internals/to-integer.js");
var max = Math.max;
var min = Math.min;

// Helper for a popular repeating case of the spec:
// Let integer be ? ToInteger(index).
// If integer < 0, let result be max((length + integer), 0); else let result be min(length, length).
module.exports = function (index, length) {
  var integer = toInteger(index);
  return integer < 0 ? max(integer + length, 0) : min(integer, length);
};


/***/ }),

/***/ "./node_modules/core-js/internals/to-indexed-object.js":
/*!*************************************************************!*\
  !*** ./node_modules/core-js/internals/to-indexed-object.js ***!
  \*************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

// toObject with fallback for non-array-like ES3 strings
var IndexedObject = __webpack_require__(/*! ../internals/indexed-object */ "./node_modules/core-js/internals/indexed-object.js");
var requireObjectCoercible = __webpack_require__(/*! ../internals/require-object-coercible */ "./node_modules/core-js/internals/require-object-coercible.js");

module.exports = function (it) {
  return IndexedObject(requireObjectCoercible(it));
};


/***/ }),

/***/ "./node_modules/core-js/internals/to-integer.js":
/*!******************************************************!*\
  !*** ./node_modules/core-js/internals/to-integer.js ***!
  \******************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

var ceil = Math.ceil;
var floor = Math.floor;

// `ToInteger` abstract operation
// https://tc39.github.io/ecma262/#sec-tointeger
module.exports = function (argument) {
  return isNaN(argument = +argument) ? 0 : (argument > 0 ? floor : ceil)(argument);
};


/***/ }),

/***/ "./node_modules/core-js/internals/to-length.js":
/*!*****************************************************!*\
  !*** ./node_modules/core-js/internals/to-length.js ***!
  \*****************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var toInteger = __webpack_require__(/*! ../internals/to-integer */ "./node_modules/core-js/internals/to-integer.js");
var min = Math.min;

// `ToLength` abstract operation
// https://tc39.github.io/ecma262/#sec-tolength
module.exports = function (argument) {
  return argument > 0 ? min(toInteger(argument), 0x1FFFFFFFFFFFFF) : 0; // 2 ** 53 - 1 == 9007199254740991
};


/***/ }),

/***/ "./node_modules/core-js/internals/to-object.js":
/*!*****************************************************!*\
  !*** ./node_modules/core-js/internals/to-object.js ***!
  \*****************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var requireObjectCoercible = __webpack_require__(/*! ../internals/require-object-coercible */ "./node_modules/core-js/internals/require-object-coercible.js");

// `ToObject` abstract operation
// https://tc39.github.io/ecma262/#sec-toobject
module.exports = function (argument) {
  return Object(requireObjectCoercible(argument));
};


/***/ }),

/***/ "./node_modules/core-js/internals/to-primitive.js":
/*!********************************************************!*\
  !*** ./node_modules/core-js/internals/to-primitive.js ***!
  \********************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

// 7.1.1 ToPrimitive(input [, PreferredType])
var isObject = __webpack_require__(/*! ../internals/is-object */ "./node_modules/core-js/internals/is-object.js");
// instead of the ES6 spec version, we didn't implement @@toPrimitive case
// and the second argument - flag - preferred type is a string
module.exports = function (it, S) {
  if (!isObject(it)) return it;
  var fn, val;
  if (S && typeof (fn = it.toString) == 'function' && !isObject(val = fn.call(it))) return val;
  if (typeof (fn = it.valueOf) == 'function' && !isObject(val = fn.call(it))) return val;
  if (!S && typeof (fn = it.toString) == 'function' && !isObject(val = fn.call(it))) return val;
  throw TypeError("Can't convert object to primitive value");
};


/***/ }),

/***/ "./node_modules/core-js/internals/uid.js":
/*!***********************************************!*\
  !*** ./node_modules/core-js/internals/uid.js ***!
  \***********************************************/
/*! no static exports found */
/***/ (function(module, exports) {

var id = 0;
var postfix = Math.random();

module.exports = function (key) {
  return 'Symbol('.concat(key === undefined ? '' : key, ')_', (++id + postfix).toString(36));
};


/***/ }),

/***/ "./node_modules/core-js/internals/user-agent.js":
/*!******************************************************!*\
  !*** ./node_modules/core-js/internals/user-agent.js ***!
  \******************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(/*! ../internals/global */ "./node_modules/core-js/internals/global.js");
var navigator = global.navigator;

module.exports = navigator && navigator.userAgent || '';


/***/ }),

/***/ "./node_modules/core-js/internals/validate-set-prototype-of-arguments.js":
/*!*******************************************************************************!*\
  !*** ./node_modules/core-js/internals/validate-set-prototype-of-arguments.js ***!
  \*******************************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var isObject = __webpack_require__(/*! ../internals/is-object */ "./node_modules/core-js/internals/is-object.js");
var anObject = __webpack_require__(/*! ../internals/an-object */ "./node_modules/core-js/internals/an-object.js");

module.exports = function (O, proto) {
  anObject(O);
  if (!isObject(proto) && proto !== null) {
    throw TypeError("Can't set " + String(proto) + ' as a prototype');
  }
};


/***/ }),

/***/ "./node_modules/core-js/internals/well-known-symbol.js":
/*!*************************************************************!*\
  !*** ./node_modules/core-js/internals/well-known-symbol.js ***!
  \*************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var store = __webpack_require__(/*! ../internals/shared */ "./node_modules/core-js/internals/shared.js")('wks');
var uid = __webpack_require__(/*! ../internals/uid */ "./node_modules/core-js/internals/uid.js");
var Symbol = __webpack_require__(/*! ../internals/global */ "./node_modules/core-js/internals/global.js").Symbol;
var NATIVE_SYMBOL = __webpack_require__(/*! ../internals/native-symbol */ "./node_modules/core-js/internals/native-symbol.js");

module.exports = function (name) {
  return store[name] || (store[name] = NATIVE_SYMBOL && Symbol[name]
    || (NATIVE_SYMBOL ? Symbol : uid)('Symbol.' + name));
};


/***/ }),

/***/ "./node_modules/core-js/modules/es.array.find.js":
/*!*******************************************************!*\
  !*** ./node_modules/core-js/modules/es.array.find.js ***!
  \*******************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var internalFind = __webpack_require__(/*! ../internals/array-methods */ "./node_modules/core-js/internals/array-methods.js")(5);
var FIND = 'find';
var SKIPS_HOLES = true;

// Shouldn't skip holes
if (FIND in []) Array(1)[FIND](function () { SKIPS_HOLES = false; });

// `Array.prototype.find` method
// https://tc39.github.io/ecma262/#sec-array.prototype.find
__webpack_require__(/*! ../internals/export */ "./node_modules/core-js/internals/export.js")({ target: 'Array', proto: true, forced: SKIPS_HOLES }, {
  find: function find(callbackfn /* , that = undefined */) {
    return internalFind(this, callbackfn, arguments.length > 1 ? arguments[1] : undefined);
  }
});

// https://tc39.github.io/ecma262/#sec-array.prototype-@@unscopables
__webpack_require__(/*! ../internals/add-to-unscopables */ "./node_modules/core-js/internals/add-to-unscopables.js")(FIND);


/***/ }),

/***/ "./node_modules/core-js/modules/es.array.iterator.js":
/*!***********************************************************!*\
  !*** ./node_modules/core-js/modules/es.array.iterator.js ***!
  \***********************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var toIndexedObject = __webpack_require__(/*! ../internals/to-indexed-object */ "./node_modules/core-js/internals/to-indexed-object.js");
var addToUnscopables = __webpack_require__(/*! ../internals/add-to-unscopables */ "./node_modules/core-js/internals/add-to-unscopables.js");
var Iterators = __webpack_require__(/*! ../internals/iterators */ "./node_modules/core-js/internals/iterators.js");
var InternalStateModule = __webpack_require__(/*! ../internals/internal-state */ "./node_modules/core-js/internals/internal-state.js");
var defineIterator = __webpack_require__(/*! ../internals/define-iterator */ "./node_modules/core-js/internals/define-iterator.js");
var ARRAY_ITERATOR = 'Array Iterator';
var setInternalState = InternalStateModule.set;
var getInternalState = InternalStateModule.getterFor(ARRAY_ITERATOR);

// `Array.prototype.entries` method
// https://tc39.github.io/ecma262/#sec-array.prototype.entries
// `Array.prototype.keys` method
// https://tc39.github.io/ecma262/#sec-array.prototype.keys
// `Array.prototype.values` method
// https://tc39.github.io/ecma262/#sec-array.prototype.values
// `Array.prototype[@@iterator]` method
// https://tc39.github.io/ecma262/#sec-array.prototype-@@iterator
// `CreateArrayIterator` internal method
// https://tc39.github.io/ecma262/#sec-createarrayiterator
module.exports = defineIterator(Array, 'Array', function (iterated, kind) {
  setInternalState(this, {
    type: ARRAY_ITERATOR,
    target: toIndexedObject(iterated), // target
    index: 0,                          // next index
    kind: kind                         // kind
  });
// `%ArrayIteratorPrototype%.next` method
// https://tc39.github.io/ecma262/#sec-%arrayiteratorprototype%.next
}, function () {
  var state = getInternalState(this);
  var target = state.target;
  var kind = state.kind;
  var index = state.index++;
  if (!target || index >= target.length) {
    state.target = undefined;
    return { value: undefined, done: true };
  }
  if (kind == 'keys') return { value: index, done: false };
  if (kind == 'values') return { value: target[index], done: false };
  return { value: [index, target[index]], done: false };
}, 'values');

// argumentsList[@@iterator] is %ArrayProto_values%
// https://tc39.github.io/ecma262/#sec-createunmappedargumentsobject
// https://tc39.github.io/ecma262/#sec-createmappedargumentsobject
Iterators.Arguments = Iterators.Array;

// https://tc39.github.io/ecma262/#sec-array.prototype-@@unscopables
addToUnscopables('keys');
addToUnscopables('values');
addToUnscopables('entries');


/***/ }),

/***/ "./node_modules/core-js/modules/es.object.assign.js":
/*!**********************************************************!*\
  !*** ./node_modules/core-js/modules/es.object.assign.js ***!
  \**********************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var assign = __webpack_require__(/*! ../internals/object-assign */ "./node_modules/core-js/internals/object-assign.js");

// `Object.assign` method
// https://tc39.github.io/ecma262/#sec-object.assign
__webpack_require__(/*! ../internals/export */ "./node_modules/core-js/internals/export.js")({ target: 'Object', stat: true, forced: Object.assign !== assign }, { assign: assign });


/***/ }),

/***/ "./node_modules/core-js/modules/es.object.to-string.js":
/*!*************************************************************!*\
  !*** ./node_modules/core-js/modules/es.object.to-string.js ***!
  \*************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var toString = __webpack_require__(/*! ../internals/object-to-string */ "./node_modules/core-js/internals/object-to-string.js");
var ObjectPrototype = Object.prototype;

// `Object.prototype.toString` method
// https://tc39.github.io/ecma262/#sec-object.prototype.tostring
if (toString !== ObjectPrototype.toString) {
  __webpack_require__(/*! ../internals/redefine */ "./node_modules/core-js/internals/redefine.js")(ObjectPrototype, 'toString', toString, { unsafe: true });
}


/***/ }),

/***/ "./node_modules/core-js/modules/es.promise.finally.js":
/*!************************************************************!*\
  !*** ./node_modules/core-js/modules/es.promise.finally.js ***!
  \************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var getBuiltIn = __webpack_require__(/*! ../internals/get-built-in */ "./node_modules/core-js/internals/get-built-in.js");
var speciesConstructor = __webpack_require__(/*! ../internals/species-constructor */ "./node_modules/core-js/internals/species-constructor.js");
var promiseResolve = __webpack_require__(/*! ../internals/promise-resolve */ "./node_modules/core-js/internals/promise-resolve.js");

// `Promise.prototype.finally` method
// https://tc39.github.io/ecma262/#sec-promise.prototype.finally
__webpack_require__(/*! ../internals/export */ "./node_modules/core-js/internals/export.js")({ target: 'Promise', proto: true, real: true }, {
  'finally': function (onFinally) {
    var C = speciesConstructor(this, getBuiltIn('Promise'));
    var isFunction = typeof onFinally == 'function';
    return this.then(
      isFunction ? function (x) {
        return promiseResolve(C, onFinally()).then(function () { return x; });
      } : onFinally,
      isFunction ? function (e) {
        return promiseResolve(C, onFinally()).then(function () { throw e; });
      } : onFinally
    );
  }
});


/***/ }),

/***/ "./node_modules/core-js/modules/es.promise.js":
/*!****************************************************!*\
  !*** ./node_modules/core-js/modules/es.promise.js ***!
  \****************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var PROMISE = 'Promise';
var IS_PURE = __webpack_require__(/*! ../internals/is-pure */ "./node_modules/core-js/internals/is-pure.js");
var global = __webpack_require__(/*! ../internals/global */ "./node_modules/core-js/internals/global.js");
var $export = __webpack_require__(/*! ../internals/export */ "./node_modules/core-js/internals/export.js");
var isObject = __webpack_require__(/*! ../internals/is-object */ "./node_modules/core-js/internals/is-object.js");
var aFunction = __webpack_require__(/*! ../internals/a-function */ "./node_modules/core-js/internals/a-function.js");
var anInstance = __webpack_require__(/*! ../internals/an-instance */ "./node_modules/core-js/internals/an-instance.js");
var classof = __webpack_require__(/*! ../internals/classof-raw */ "./node_modules/core-js/internals/classof-raw.js");
var iterate = __webpack_require__(/*! ../internals/iterate */ "./node_modules/core-js/internals/iterate.js");
var checkCorrectnessOfIteration = __webpack_require__(/*! ../internals/check-correctness-of-iteration */ "./node_modules/core-js/internals/check-correctness-of-iteration.js");
var speciesConstructor = __webpack_require__(/*! ../internals/species-constructor */ "./node_modules/core-js/internals/species-constructor.js");
var task = __webpack_require__(/*! ../internals/task */ "./node_modules/core-js/internals/task.js").set;
var microtask = __webpack_require__(/*! ../internals/microtask */ "./node_modules/core-js/internals/microtask.js");
var promiseResolve = __webpack_require__(/*! ../internals/promise-resolve */ "./node_modules/core-js/internals/promise-resolve.js");
var hostReportErrors = __webpack_require__(/*! ../internals/host-report-errors */ "./node_modules/core-js/internals/host-report-errors.js");
var newPromiseCapabilityModule = __webpack_require__(/*! ../internals/new-promise-capability */ "./node_modules/core-js/internals/new-promise-capability.js");
var perform = __webpack_require__(/*! ../internals/perform */ "./node_modules/core-js/internals/perform.js");
var userAgent = __webpack_require__(/*! ../internals/user-agent */ "./node_modules/core-js/internals/user-agent.js");
var SPECIES = __webpack_require__(/*! ../internals/well-known-symbol */ "./node_modules/core-js/internals/well-known-symbol.js")('species');
var InternalStateModule = __webpack_require__(/*! ../internals/internal-state */ "./node_modules/core-js/internals/internal-state.js");
var isForced = __webpack_require__(/*! ../internals/is-forced */ "./node_modules/core-js/internals/is-forced.js");
var getInternalState = InternalStateModule.get;
var setInternalState = InternalStateModule.set;
var getInternalPromiseState = InternalStateModule.getterFor(PROMISE);
var PromiseConstructor = global[PROMISE];
var TypeError = global.TypeError;
var document = global.document;
var process = global.process;
var $fetch = global.fetch;
var versions = process && process.versions;
var v8 = versions && versions.v8 || '';
var newPromiseCapability = newPromiseCapabilityModule.f;
var newGenericPromiseCapability = newPromiseCapability;
var IS_NODE = classof(process) == 'process';
var DISPATCH_EVENT = !!(document && document.createEvent && global.dispatchEvent);
var UNHANDLED_REJECTION = 'unhandledrejection';
var REJECTION_HANDLED = 'rejectionhandled';
var PENDING = 0;
var FULFILLED = 1;
var REJECTED = 2;
var HANDLED = 1;
var UNHANDLED = 2;
var Internal, OwnPromiseCapability, PromiseWrapper;

var FORCED = isForced(PROMISE, function () {
  // correct subclassing with @@species support
  var promise = PromiseConstructor.resolve(1);
  var empty = function () { /* empty */ };
  var FakePromise = (promise.constructor = {})[SPECIES] = function (exec) {
    exec(empty, empty);
  };
  // unhandled rejections tracking support, NodeJS Promise without it fails @@species test
  return !((IS_NODE || typeof PromiseRejectionEvent == 'function')
    && (!IS_PURE || promise['finally'])
    && promise.then(empty) instanceof FakePromise
    // v8 6.6 (Node 10 and Chrome 66) have a bug with resolving custom thenables
    // https://bugs.chromium.org/p/chromium/issues/detail?id=830565
    // we can't detect it synchronously, so just check versions
    && v8.indexOf('6.6') !== 0
    && userAgent.indexOf('Chrome/66') === -1);
});

var INCORRECT_ITERATION = FORCED || !checkCorrectnessOfIteration(function (iterable) {
  PromiseConstructor.all(iterable)['catch'](function () { /* empty */ });
});

// helpers
var isThenable = function (it) {
  var then;
  return isObject(it) && typeof (then = it.then) == 'function' ? then : false;
};

var notify = function (promise, state, isReject) {
  if (state.notified) return;
  state.notified = true;
  var chain = state.reactions;
  microtask(function () {
    var value = state.value;
    var ok = state.state == FULFILLED;
    var i = 0;
    var run = function (reaction) {
      var handler = ok ? reaction.ok : reaction.fail;
      var resolve = reaction.resolve;
      var reject = reaction.reject;
      var domain = reaction.domain;
      var result, then, exited;
      try {
        if (handler) {
          if (!ok) {
            if (state.rejection === UNHANDLED) onHandleUnhandled(promise, state);
            state.rejection = HANDLED;
          }
          if (handler === true) result = value;
          else {
            if (domain) domain.enter();
            result = handler(value); // may throw
            if (domain) {
              domain.exit();
              exited = true;
            }
          }
          if (result === reaction.promise) {
            reject(TypeError('Promise-chain cycle'));
          } else if (then = isThenable(result)) {
            then.call(result, resolve, reject);
          } else resolve(result);
        } else reject(value);
      } catch (error) {
        if (domain && !exited) domain.exit();
        reject(error);
      }
    };
    while (chain.length > i) run(chain[i++]); // variable length - can't use forEach
    state.reactions = [];
    state.notified = false;
    if (isReject && !state.rejection) onUnhandled(promise, state);
  });
};

var dispatchEvent = function (name, promise, reason) {
  var event, handler;
  if (DISPATCH_EVENT) {
    event = document.createEvent('Event');
    event.promise = promise;
    event.reason = reason;
    event.initEvent(name, false, true);
    global.dispatchEvent(event);
  } else event = { promise: promise, reason: reason };
  if (handler = global['on' + name]) handler(event);
  else if (name === UNHANDLED_REJECTION) hostReportErrors('Unhandled promise rejection', reason);
};

var onUnhandled = function (promise, state) {
  task.call(global, function () {
    var value = state.value;
    var IS_UNHANDLED = isUnhandled(state);
    var result;
    if (IS_UNHANDLED) {
      result = perform(function () {
        if (IS_NODE) {
          process.emit('unhandledRejection', value, promise);
        } else dispatchEvent(UNHANDLED_REJECTION, promise, value);
      });
      // Browsers should not trigger `rejectionHandled` event if it was handled here, NodeJS - should
      state.rejection = IS_NODE || isUnhandled(state) ? UNHANDLED : HANDLED;
      if (result.error) throw result.value;
    }
  });
};

var isUnhandled = function (state) {
  return state.rejection !== HANDLED && !state.parent;
};

var onHandleUnhandled = function (promise, state) {
  task.call(global, function () {
    if (IS_NODE) {
      process.emit('rejectionHandled', promise);
    } else dispatchEvent(REJECTION_HANDLED, promise, state.value);
  });
};

var bind = function (fn, promise, state, unwrap) {
  return function (value) {
    fn(promise, state, value, unwrap);
  };
};

var internalReject = function (promise, state, value, unwrap) {
  if (state.done) return;
  state.done = true;
  if (unwrap) state = unwrap;
  state.value = value;
  state.state = REJECTED;
  notify(promise, state, true);
};

var internalResolve = function (promise, state, value, unwrap) {
  if (state.done) return;
  state.done = true;
  if (unwrap) state = unwrap;
  try {
    if (promise === value) throw TypeError("Promise can't be resolved itself");
    var then = isThenable(value);
    if (then) {
      microtask(function () {
        var wrapper = { done: false };
        try {
          then.call(value,
            bind(internalResolve, promise, wrapper, state),
            bind(internalReject, promise, wrapper, state)
          );
        } catch (error) {
          internalReject(promise, wrapper, error, state);
        }
      });
    } else {
      state.value = value;
      state.state = FULFILLED;
      notify(promise, state, false);
    }
  } catch (error) {
    internalReject(promise, { done: false }, error, state);
  }
};

// constructor polyfill
if (FORCED) {
  // 25.4.3.1 Promise(executor)
  PromiseConstructor = function Promise(executor) {
    anInstance(this, PromiseConstructor, PROMISE);
    aFunction(executor);
    Internal.call(this);
    var state = getInternalState(this);
    try {
      executor(bind(internalResolve, this, state), bind(internalReject, this, state));
    } catch (error) {
      internalReject(this, state, error);
    }
  };
  // eslint-disable-next-line no-unused-vars
  Internal = function Promise(executor) {
    setInternalState(this, {
      type: PROMISE,
      done: false,
      notified: false,
      parent: false,
      reactions: [],
      rejection: false,
      state: PENDING,
      value: undefined
    });
  };
  Internal.prototype = __webpack_require__(/*! ../internals/redefine-all */ "./node_modules/core-js/internals/redefine-all.js")(PromiseConstructor.prototype, {
    // `Promise.prototype.then` method
    // https://tc39.github.io/ecma262/#sec-promise.prototype.then
    then: function then(onFulfilled, onRejected) {
      var state = getInternalPromiseState(this);
      var reaction = newPromiseCapability(speciesConstructor(this, PromiseConstructor));
      reaction.ok = typeof onFulfilled == 'function' ? onFulfilled : true;
      reaction.fail = typeof onRejected == 'function' && onRejected;
      reaction.domain = IS_NODE ? process.domain : undefined;
      state.parent = true;
      state.reactions.push(reaction);
      if (state.state != PENDING) notify(this, state, false);
      return reaction.promise;
    },
    // `Promise.prototype.catch` method
    // https://tc39.github.io/ecma262/#sec-promise.prototype.catch
    'catch': function (onRejected) {
      return this.then(undefined, onRejected);
    }
  });
  OwnPromiseCapability = function () {
    var promise = new Internal();
    var state = getInternalState(promise);
    this.promise = promise;
    this.resolve = bind(internalResolve, promise, state);
    this.reject = bind(internalReject, promise, state);
  };
  newPromiseCapabilityModule.f = newPromiseCapability = function (C) {
    return C === PromiseConstructor || C === PromiseWrapper
      ? new OwnPromiseCapability(C)
      : newGenericPromiseCapability(C);
  };

  // wrap fetch result
  if (!IS_PURE && typeof $fetch == 'function') $export({ global: true, enumerable: true, forced: true }, {
    // eslint-disable-next-line no-unused-vars
    fetch: function fetch(input) {
      return promiseResolve(PromiseConstructor, $fetch.apply(global, arguments));
    }
  });
}

$export({ global: true, wrap: true, forced: FORCED }, { Promise: PromiseConstructor });

__webpack_require__(/*! ../internals/set-to-string-tag */ "./node_modules/core-js/internals/set-to-string-tag.js")(PromiseConstructor, PROMISE, false, true);
__webpack_require__(/*! ../internals/set-species */ "./node_modules/core-js/internals/set-species.js")(PROMISE);

PromiseWrapper = __webpack_require__(/*! ../internals/path */ "./node_modules/core-js/internals/path.js")[PROMISE];

// statics
$export({ target: PROMISE, stat: true, forced: FORCED }, {
  // `Promise.reject` method
  // https://tc39.github.io/ecma262/#sec-promise.reject
  reject: function reject(r) {
    var capability = newPromiseCapability(this);
    capability.reject.call(undefined, r);
    return capability.promise;
  }
});

$export({ target: PROMISE, stat: true, forced: IS_PURE || FORCED }, {
  // `Promise.resolve` method
  // https://tc39.github.io/ecma262/#sec-promise.resolve
  resolve: function resolve(x) {
    return promiseResolve(IS_PURE && this === PromiseWrapper ? PromiseConstructor : this, x);
  }
});

$export({ target: PROMISE, stat: true, forced: INCORRECT_ITERATION }, {
  // `Promise.all` method
  // https://tc39.github.io/ecma262/#sec-promise.all
  all: function all(iterable) {
    var C = this;
    var capability = newPromiseCapability(C);
    var resolve = capability.resolve;
    var reject = capability.reject;
    var result = perform(function () {
      var values = [];
      var counter = 0;
      var remaining = 1;
      iterate(iterable, function (promise) {
        var index = counter++;
        var alreadyCalled = false;
        values.push(undefined);
        remaining++;
        C.resolve(promise).then(function (value) {
          if (alreadyCalled) return;
          alreadyCalled = true;
          values[index] = value;
          --remaining || resolve(values);
        }, reject);
      });
      --remaining || resolve(values);
    });
    if (result.error) reject(result.value);
    return capability.promise;
  },
  // `Promise.race` method
  // https://tc39.github.io/ecma262/#sec-promise.race
  race: function race(iterable) {
    var C = this;
    var capability = newPromiseCapability(C);
    var reject = capability.reject;
    var result = perform(function () {
      iterate(iterable, function (promise) {
        C.resolve(promise).then(capability.resolve, reject);
      });
    });
    if (result.error) reject(result.value);
    return capability.promise;
  }
});


/***/ }),

/***/ "./node_modules/core-js/modules/es.string.iterator.js":
/*!************************************************************!*\
  !*** ./node_modules/core-js/modules/es.string.iterator.js ***!
  \************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var codePointAt = __webpack_require__(/*! ../internals/string-at */ "./node_modules/core-js/internals/string-at.js");
var InternalStateModule = __webpack_require__(/*! ../internals/internal-state */ "./node_modules/core-js/internals/internal-state.js");
var defineIterator = __webpack_require__(/*! ../internals/define-iterator */ "./node_modules/core-js/internals/define-iterator.js");
var STRING_ITERATOR = 'String Iterator';
var setInternalState = InternalStateModule.set;
var getInternalState = InternalStateModule.getterFor(STRING_ITERATOR);

// `String.prototype[@@iterator]` method
// https://tc39.github.io/ecma262/#sec-string.prototype-@@iterator
defineIterator(String, 'String', function (iterated) {
  setInternalState(this, {
    type: STRING_ITERATOR,
    string: String(iterated),
    index: 0
  });
// `%StringIteratorPrototype%.next` method
// https://tc39.github.io/ecma262/#sec-%stringiteratorprototype%.next
}, function next() {
  var state = getInternalState(this);
  var string = state.string;
  var index = state.index;
  var point;
  if (index >= string.length) return { value: undefined, done: true };
  point = codePointAt(string, index, true);
  state.index += point.length;
  return { value: point, done: false };
});


/***/ }),

/***/ "./node_modules/core-js/modules/esnext.aggregate-error.js":
/*!****************************************************************!*\
  !*** ./node_modules/core-js/modules/esnext.aggregate-error.js ***!
  \****************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var getPrototypeOf = __webpack_require__(/*! ../internals/object-get-prototype-of */ "./node_modules/core-js/internals/object-get-prototype-of.js");
var setPrototypeOf = __webpack_require__(/*! ../internals/object-set-prototype-of */ "./node_modules/core-js/internals/object-set-prototype-of.js");
var create = __webpack_require__(/*! ../internals/object-create */ "./node_modules/core-js/internals/object-create.js");
var iterate = __webpack_require__(/*! ../internals/iterate */ "./node_modules/core-js/internals/iterate.js");
var hide = __webpack_require__(/*! ../internals/hide */ "./node_modules/core-js/internals/hide.js");

var $AggregateError = function AggregateError(errors, message) {
  var that = this;
  if (!(that instanceof $AggregateError)) return new $AggregateError(errors, message);
  if (setPrototypeOf) {
    that = setPrototypeOf(new Error(message), getPrototypeOf(that));
  }
  var errorsArray = [];
  iterate(errors, errorsArray.push, errorsArray);
  that.errors = errorsArray;
  if (message !== undefined) hide(that, 'message', String(message));
  return that;
};

$AggregateError.prototype = create(Error.prototype, {
  constructor: { value: $AggregateError, configurable: true, writable: true },
  name: { value: 'AggregateError', configurable: true, writable: true }
});

__webpack_require__(/*! ../internals/export */ "./node_modules/core-js/internals/export.js")({ global: true }, {
  AggregateError: $AggregateError
});


/***/ }),

/***/ "./node_modules/core-js/modules/esnext.promise.all-settled.js":
/*!********************************************************************!*\
  !*** ./node_modules/core-js/modules/esnext.promise.all-settled.js ***!
  \********************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

// `Promise.allSettled` method
// https://github.com/tc39/proposal-promise-allSettled
var newPromiseCapabilityModule = __webpack_require__(/*! ../internals/new-promise-capability */ "./node_modules/core-js/internals/new-promise-capability.js");
var perform = __webpack_require__(/*! ../internals/perform */ "./node_modules/core-js/internals/perform.js");
var iterate = __webpack_require__(/*! ../internals/iterate */ "./node_modules/core-js/internals/iterate.js");

__webpack_require__(/*! ../internals/export */ "./node_modules/core-js/internals/export.js")({ target: 'Promise', stat: true }, {
  allSettled: function allSettled(iterable) {
    var C = this;
    var capability = newPromiseCapabilityModule.f(C);
    var resolve = capability.resolve;
    var reject = capability.reject;
    var result = perform(function () {
      var values = [];
      var counter = 0;
      var remaining = 1;
      iterate(iterable, function (promise) {
        var index = counter++;
        var alreadyCalled = false;
        values.push(undefined);
        remaining++;
        C.resolve(promise).then(function (value) {
          if (alreadyCalled) return;
          alreadyCalled = true;
          values[index] = { status: 'fulfilled', value: value };
          --remaining || resolve(values);
        }, function (e) {
          if (alreadyCalled) return;
          alreadyCalled = true;
          values[index] = { status: 'rejected', reason: e };
          --remaining || resolve(values);
        });
      });
      --remaining || resolve(values);
    });
    if (result.error) reject(result.value);
    return capability.promise;
  }
});


/***/ }),

/***/ "./node_modules/core-js/modules/esnext.promise.any.js":
/*!************************************************************!*\
  !*** ./node_modules/core-js/modules/esnext.promise.any.js ***!
  \************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

// `Promise.any` method
// https://github.com/tc39/proposal-promise-any
var getBuiltIn = __webpack_require__(/*! ../internals/get-built-in */ "./node_modules/core-js/internals/get-built-in.js");
var newPromiseCapabilityModule = __webpack_require__(/*! ../internals/new-promise-capability */ "./node_modules/core-js/internals/new-promise-capability.js");
var perform = __webpack_require__(/*! ../internals/perform */ "./node_modules/core-js/internals/perform.js");
var iterate = __webpack_require__(/*! ../internals/iterate */ "./node_modules/core-js/internals/iterate.js");
var PROMISE_ANY_ERROR = 'No one promise resolved';

__webpack_require__(/*! ../internals/export */ "./node_modules/core-js/internals/export.js")({ target: 'Promise', stat: true }, {
  any: function any(iterable) {
    var C = this;
    var capability = newPromiseCapabilityModule.f(C);
    var resolve = capability.resolve;
    var reject = capability.reject;
    var result = perform(function () {
      var errors = [];
      var counter = 0;
      var remaining = 1;
      var alreadyResolved = false;
      iterate(iterable, function (promise) {
        var index = counter++;
        var alreadyRejected = false;
        errors.push(undefined);
        remaining++;
        C.resolve(promise).then(function (value) {
          if (alreadyRejected || alreadyResolved) return;
          alreadyResolved = true;
          resolve(value);
        }, function (e) {
          if (alreadyRejected || alreadyResolved) return;
          alreadyRejected = true;
          errors[index] = e;
          --remaining || reject(new (getBuiltIn('AggregateError'))(errors, PROMISE_ANY_ERROR));
        });
      });
      --remaining || reject(new (getBuiltIn('AggregateError'))(errors, PROMISE_ANY_ERROR));
    });
    if (result.error) reject(result.value);
    return capability.promise;
  }
});


/***/ }),

/***/ "./node_modules/core-js/modules/esnext.promise.try.js":
/*!************************************************************!*\
  !*** ./node_modules/core-js/modules/esnext.promise.try.js ***!
  \************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

// `Promise.try` method
// https://github.com/tc39/proposal-promise-try
var newPromiseCapabilityModule = __webpack_require__(/*! ../internals/new-promise-capability */ "./node_modules/core-js/internals/new-promise-capability.js");
var perform = __webpack_require__(/*! ../internals/perform */ "./node_modules/core-js/internals/perform.js");

__webpack_require__(/*! ../internals/export */ "./node_modules/core-js/internals/export.js")({ target: 'Promise', stat: true }, {
  'try': function (callbackfn) {
    var promiseCapability = newPromiseCapabilityModule.f(this);
    var result = perform(callbackfn);
    (result.error ? promiseCapability.reject : promiseCapability.resolve)(result.value);
    return promiseCapability.promise;
  }
});


/***/ }),

/***/ "./node_modules/core-js/modules/web.dom-collections.iterator.js":
/*!**********************************************************************!*\
  !*** ./node_modules/core-js/modules/web.dom-collections.iterator.js ***!
  \**********************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

var DOMIterables = __webpack_require__(/*! ../internals/dom-iterables */ "./node_modules/core-js/internals/dom-iterables.js");
var ArrayIteratorMethods = __webpack_require__(/*! ../modules/es.array.iterator */ "./node_modules/core-js/modules/es.array.iterator.js");
var global = __webpack_require__(/*! ../internals/global */ "./node_modules/core-js/internals/global.js");
var hide = __webpack_require__(/*! ../internals/hide */ "./node_modules/core-js/internals/hide.js");
var wellKnownSymbol = __webpack_require__(/*! ../internals/well-known-symbol */ "./node_modules/core-js/internals/well-known-symbol.js");
var ITERATOR = wellKnownSymbol('iterator');
var TO_STRING_TAG = wellKnownSymbol('toStringTag');
var ArrayValues = ArrayIteratorMethods.values;

for (var COLLECTION_NAME in DOMIterables) {
  var Collection = global[COLLECTION_NAME];
  var CollectionPrototype = Collection && Collection.prototype;
  if (CollectionPrototype) {
    // some Chrome versions have non-configurable methods on DOMTokenList
    if (CollectionPrototype[ITERATOR] !== ArrayValues) try {
      hide(CollectionPrototype, ITERATOR, ArrayValues);
    } catch (error) {
      CollectionPrototype[ITERATOR] = ArrayValues;
    }
    if (!CollectionPrototype[TO_STRING_TAG]) hide(CollectionPrototype, TO_STRING_TAG, COLLECTION_NAME);
    if (DOMIterables[COLLECTION_NAME]) for (var METHOD_NAME in ArrayIteratorMethods) {
      // some Chrome versions have non-configurable methods on DOMTokenList
      if (CollectionPrototype[METHOD_NAME] !== ArrayIteratorMethods[METHOD_NAME]) try {
        hide(CollectionPrototype, METHOD_NAME, ArrayIteratorMethods[METHOD_NAME]);
      } catch (error) {
        CollectionPrototype[METHOD_NAME] = ArrayIteratorMethods[METHOD_NAME];
      }
    }
  }
}


/***/ }),

/***/ "./node_modules/tslib/tslib.es6.js":
/*!*****************************************!*\
  !*** ./node_modules/tslib/tslib.es6.js ***!
  \*****************************************/
/*! exports provided: __extends, __assign, __rest, __decorate, __param, __metadata, __awaiter, __generator, __exportStar, __values, __read, __spread, __await, __asyncGenerator, __asyncDelegator, __asyncValues, __makeTemplateObject, __importStar, __importDefault */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "__extends", function() { return __extends; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "__assign", function() { return __assign; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "__rest", function() { return __rest; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "__decorate", function() { return __decorate; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "__param", function() { return __param; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "__metadata", function() { return __metadata; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "__awaiter", function() { return __awaiter; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "__generator", function() { return __generator; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "__exportStar", function() { return __exportStar; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "__values", function() { return __values; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "__read", function() { return __read; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "__spread", function() { return __spread; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "__await", function() { return __await; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "__asyncGenerator", function() { return __asyncGenerator; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "__asyncDelegator", function() { return __asyncDelegator; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "__asyncValues", function() { return __asyncValues; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "__makeTemplateObject", function() { return __makeTemplateObject; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "__importStar", function() { return __importStar; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "__importDefault", function() { return __importDefault; });
/*! *****************************************************************************
Copyright (c) Microsoft Corporation. All rights reserved.
Licensed under the Apache License, Version 2.0 (the "License"); you may not use
this file except in compliance with the License. You may obtain a copy of the
License at http://www.apache.org/licenses/LICENSE-2.0

THIS CODE IS PROVIDED ON AN *AS IS* BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, EITHER EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION ANY IMPLIED
WARRANTIES OR CONDITIONS OF TITLE, FITNESS FOR A PARTICULAR PURPOSE,
MERCHANTABLITY OR NON-INFRINGEMENT.

See the Apache Version 2.0 License for specific language governing permissions
and limitations under the License.
***************************************************************************** */
/* global Reflect, Promise */

var extendStatics = function(d, b) {
    extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return extendStatics(d, b);
};

function __extends(d, b) {
    extendStatics(d, b);
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
}

var __assign = function() {
    __assign = Object.assign || function __assign(t) {
        for (var s, i = 1, n = arguments.length; i < n; i++) {
            s = arguments[i];
            for (var p in s) if (Object.prototype.hasOwnProperty.call(s, p)) t[p] = s[p];
        }
        return t;
    }
    return __assign.apply(this, arguments);
}

function __rest(s, e) {
    var t = {};
    for (var p in s) if (Object.prototype.hasOwnProperty.call(s, p) && e.indexOf(p) < 0)
        t[p] = s[p];
    if (s != null && typeof Object.getOwnPropertySymbols === "function")
        for (var i = 0, p = Object.getOwnPropertySymbols(s); i < p.length; i++) if (e.indexOf(p[i]) < 0)
            t[p[i]] = s[p[i]];
    return t;
}

function __decorate(decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
}

function __param(paramIndex, decorator) {
    return function (target, key) { decorator(target, key, paramIndex); }
}

function __metadata(metadataKey, metadataValue) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(metadataKey, metadataValue);
}

function __awaiter(thisArg, _arguments, P, generator) {
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : new P(function (resolve) { resolve(result.value); }).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
}

function __generator(thisArg, body) {
    var _ = { label: 0, sent: function() { if (t[0] & 1) throw t[1]; return t[1]; }, trys: [], ops: [] }, f, y, t, g;
    return g = { next: verb(0), "throw": verb(1), "return": verb(2) }, typeof Symbol === "function" && (g[Symbol.iterator] = function() { return this; }), g;
    function verb(n) { return function (v) { return step([n, v]); }; }
    function step(op) {
        if (f) throw new TypeError("Generator is already executing.");
        while (_) try {
            if (f = 1, y && (t = op[0] & 2 ? y["return"] : op[0] ? y["throw"] || ((t = y["return"]) && t.call(y), 0) : y.next) && !(t = t.call(y, op[1])).done) return t;
            if (y = 0, t) op = [op[0] & 2, t.value];
            switch (op[0]) {
                case 0: case 1: t = op; break;
                case 4: _.label++; return { value: op[1], done: false };
                case 5: _.label++; y = op[1]; op = [0]; continue;
                case 7: op = _.ops.pop(); _.trys.pop(); continue;
                default:
                    if (!(t = _.trys, t = t.length > 0 && t[t.length - 1]) && (op[0] === 6 || op[0] === 2)) { _ = 0; continue; }
                    if (op[0] === 3 && (!t || (op[1] > t[0] && op[1] < t[3]))) { _.label = op[1]; break; }
                    if (op[0] === 6 && _.label < t[1]) { _.label = t[1]; t = op; break; }
                    if (t && _.label < t[2]) { _.label = t[2]; _.ops.push(op); break; }
                    if (t[2]) _.ops.pop();
                    _.trys.pop(); continue;
            }
            op = body.call(thisArg, _);
        } catch (e) { op = [6, e]; y = 0; } finally { f = t = 0; }
        if (op[0] & 5) throw op[1]; return { value: op[0] ? op[1] : void 0, done: true };
    }
}

function __exportStar(m, exports) {
    for (var p in m) if (!exports.hasOwnProperty(p)) exports[p] = m[p];
}

function __values(o) {
    var m = typeof Symbol === "function" && o[Symbol.iterator], i = 0;
    if (m) return m.call(o);
    return {
        next: function () {
            if (o && i >= o.length) o = void 0;
            return { value: o && o[i++], done: !o };
        }
    };
}

function __read(o, n) {
    var m = typeof Symbol === "function" && o[Symbol.iterator];
    if (!m) return o;
    var i = m.call(o), r, ar = [], e;
    try {
        while ((n === void 0 || n-- > 0) && !(r = i.next()).done) ar.push(r.value);
    }
    catch (error) { e = { error: error }; }
    finally {
        try {
            if (r && !r.done && (m = i["return"])) m.call(i);
        }
        finally { if (e) throw e.error; }
    }
    return ar;
}

function __spread() {
    for (var ar = [], i = 0; i < arguments.length; i++)
        ar = ar.concat(__read(arguments[i]));
    return ar;
}

function __await(v) {
    return this instanceof __await ? (this.v = v, this) : new __await(v);
}

function __asyncGenerator(thisArg, _arguments, generator) {
    if (!Symbol.asyncIterator) throw new TypeError("Symbol.asyncIterator is not defined.");
    var g = generator.apply(thisArg, _arguments || []), i, q = [];
    return i = {}, verb("next"), verb("throw"), verb("return"), i[Symbol.asyncIterator] = function () { return this; }, i;
    function verb(n) { if (g[n]) i[n] = function (v) { return new Promise(function (a, b) { q.push([n, v, a, b]) > 1 || resume(n, v); }); }; }
    function resume(n, v) { try { step(g[n](v)); } catch (e) { settle(q[0][3], e); } }
    function step(r) { r.value instanceof __await ? Promise.resolve(r.value.v).then(fulfill, reject) : settle(q[0][2], r); }
    function fulfill(value) { resume("next", value); }
    function reject(value) { resume("throw", value); }
    function settle(f, v) { if (f(v), q.shift(), q.length) resume(q[0][0], q[0][1]); }
}

function __asyncDelegator(o) {
    var i, p;
    return i = {}, verb("next"), verb("throw", function (e) { throw e; }), verb("return"), i[Symbol.iterator] = function () { return this; }, i;
    function verb(n, f) { i[n] = o[n] ? function (v) { return (p = !p) ? { value: __await(o[n](v)), done: n === "return" } : f ? f(v) : v; } : f; }
}

function __asyncValues(o) {
    if (!Symbol.asyncIterator) throw new TypeError("Symbol.asyncIterator is not defined.");
    var m = o[Symbol.asyncIterator], i;
    return m ? m.call(o) : (o = typeof __values === "function" ? __values(o) : o[Symbol.iterator](), i = {}, verb("next"), verb("throw"), verb("return"), i[Symbol.asyncIterator] = function () { return this; }, i);
    function verb(n) { i[n] = o[n] && function (v) { return new Promise(function (resolve, reject) { v = o[n](v), settle(resolve, reject, v.done, v.value); }); }; }
    function settle(resolve, reject, d, v) { Promise.resolve(v).then(function(v) { resolve({ value: v, done: d }); }, reject); }
}

function __makeTemplateObject(cooked, raw) {
    if (Object.defineProperty) { Object.defineProperty(cooked, "raw", { value: raw }); } else { cooked.raw = raw; }
    return cooked;
};

function __importStar(mod) {
    if (mod && mod.__esModule) return mod;
    var result = {};
    if (mod != null) for (var k in mod) if (Object.hasOwnProperty.call(mod, k)) result[k] = mod[k];
    result.default = mod;
    return result;
}

function __importDefault(mod) {
    return (mod && mod.__esModule) ? mod : { default: mod };
}


/***/ }),

/***/ "./src/connect.ts":
/*!************************!*\
  !*** ./src/connect.ts ***!
  \************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

/**
 * @namespace AW4
 *
 * @property {Module} Utils {@link module:Utils}
 * @property {Module} Logger {@link module:Logger}
 */
Object.defineProperty(exports, "__esModule", { value: true });
var tslib_1 = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
var Utils = tslib_1.__importStar(__webpack_require__(/*! ./utils */ "./src/utils.ts"));
var Logger = tslib_1.__importStar(__webpack_require__(/*! ./logger */ "./src/logger.ts"));
var handler_1 = tslib_1.__importDefault(__webpack_require__(/*! ./request/handler */ "./src/request/handler.ts"));
var constants_1 = __webpack_require__(/*! ./constants */ "./src/constants/index.ts");
var api_1 = tslib_1.__importDefault(__webpack_require__(/*! ./core/api */ "./src/core/api.ts"));
var request_1 = tslib_1.__importDefault(__webpack_require__(/*! ./core/request */ "./src/core/request.ts"));
var validators_1 = __webpack_require__(/*! ./core/validators */ "./src/core/validators.ts");
var globals_1 = __webpack_require__(/*! ./helpers/globals */ "./src/helpers/globals.ts");
/**
 * @classdesc Contains all the Connect API methods
 *
 * @name Connect
 * @class
 * @memberof AW4
 * @param {Object} options Configuration parameters for Connect
 * @param {Number} [options.connectLaunchWaitTimeoutMs=5000] How long to wait in milliseconds
 *   for Connect to launch. If we reach this timeout without a successful request to Connect,
 *   Connect will go to FAILED status.
 * @param {String} [options.id="aspera-web"] The DOM 'id' of the plug-in object to be inserted.
 * @param {String} [options.containerId] The DOM 'id' of an existing element to insert the plug-in
 *   element into (replacing its contents). If not specified, the plug-in is appended to the document body.
 *   Note that the plug-in must not be hidden in order to be loaded.
 * @param {String} [options.sdkLocation="//d3gcli72yxqn2z.cloudfront.net/connect/v4"] Specifies the custom
 *   SDK location to check for Connect installers. It has to be in the following format: '//domain/path/to/connect/sdk'.
 *   If you are hosting your own SDK, then you must provide the location to your copy
 *   of the SDK. This points to the /v4 folder of the provided SDK. The URL provided
 *   must be in the same level of security as the web page (HTTP/HTTPS), HTTPS preferred.
 * @param {Number} [options.pollingTime=2000] How often in milliseconds we want to get
 *   updates of transfer status.
 * @param {Number} [options.maxActivityOutstanding=2] The maximum number of oustanding transfer activity
 *   requests allowed before being skipped.
 * @param {Number} [options.extensionRequestTimeout=86400000] How long to wait in milliseconds for extension requests
 *  to return before failing. Only applies to file and folder dialog APIs.
 * @param {String} [options.minVersion] Minimum version of Connect required by the web
 *   application in order to work. Format: "3.9.0".
 * @param {Boolean} [options.dragDropEnabled=false] Enable drag and drop of files/folders
 *   into the browser.
 * @param {("http"|"extension")} [options.connectMethod] Specify the preferred method of
 *   Connect communication. Default is "extension" for `minVersion` >= 3.9.0. Otherwise, default
 *   is "http".
 *
 * @example
 * let options = {
 *   minVersion: "3.9.0",
 *   dragDropEnabled: true
 * }
 * let asperaWeb = new AW4.Connect(options) // returns instance of AW4.Connect
 */
var ConnectClient = function ConnectClient(options) {
    var _newTarget = this && this instanceof ConnectClient ? this.constructor : void 0;
    if (!_newTarget) {
        throw new Error('Connect() must be called with new');
    }
    if (Utils.isNullOrUndefinedOrEmpty(options)) {
        options = {};
    }
    var INITIALIZE_TIMEOUT = options.connectLaunchWaitTimeoutMs || 5000;
    var PLUGIN_ID = options.id || 'aspera-web';
    var PLUGIN_CONTAINER_ID = options.containerId || 'aspera-web-container';
    var APPLICATION_ID = '';
    var AUTHORIZATION_KEY = options.authorizationKey || '';
    var POLLING_TIME = options.pollingTime || 2000;
    var MINIMUM_VERSION = options.minVersion || '';
    var CONNECT_METHOD = options.connectMethod || '';
    var DRAGDROP_ENABLED = options.dragDropEnabled || false;
    var MAX_ACTIVITY_OUTSTANDING = options.maxActivityOutstanding || 2;
    var SDK_LOCATION = Utils.getFullURI(options.sdkLocation) || '//d3gcli72yxqn2z.cloudfront.net/connect/v4';
    var EXTENSION_REQUEST_TIMEOUT = options.extensionRequestTimeout;
    // Evaluate local storage overrides
    if (typeof (Storage) !== 'undefined') {
        var overrideMethod = Utils.getLocalStorage('aspera-connect-method');
        if (overrideMethod) {
            CONNECT_METHOD = overrideMethod;
        }
        var overrideMinVersion = Utils.getLocalStorage('aspera-min-version');
        if (overrideMinVersion) {
            MINIMUM_VERSION = overrideMinVersion;
        }
    }
    // Expose the requested version to the install banner
    if (MINIMUM_VERSION) {
        globals_1.ConnectGlobals.minVersion = MINIMUM_VERSION;
    }
    var transferListeners = [];
    var transferEventIntervalId = 0;
    var transferEventIterationToken = 0;
    var statusListeners = [];
    var connectStatus = constants_1.STATUS.INITIALIZING;
    var objectId = Utils.nextObjectId();
    var outstandingActivityReqs = 0; // Keep track of polling requests to avoid overfilling the queue
    var apiReady = false;
    var requestHandler = new handler_1.default({
        id: PLUGIN_ID,
        containerId: PLUGIN_CONTAINER_ID,
        connectLaunchWaitTimeoutMs: INITIALIZE_TIMEOUT,
        sdkLocation: SDK_LOCATION,
        connectMethod: CONNECT_METHOD,
        minVersion: MINIMUM_VERSION,
        extensionRequestTimeout: EXTENSION_REQUEST_TIMEOUT,
        objectId: objectId,
        statusListener: manageConnectStatus
    });
    var api = new api_1.default(requestHandler);
    function addStandardSettings(body) {
        if (AUTHORIZATION_KEY.length !== 0) {
            body.authorization_key = AUTHORIZATION_KEY;
        }
        if (Utils.isNullOrUndefinedOrEmpty(body.aspera_connect_settings)) {
            body.aspera_connect_settings = {};
        }
        body.aspera_connect_settings.app_id = APPLICATION_ID;
        return body;
    }
    function connectReady() {
        Logger.debug('Connect API is ready.');
        apiReady = true;
        initDragDrop();
    }
    function getAllTransfersHelper(iterationToken, callbacks) {
        var data = { iteration_token: iterationToken };
        var request = new request_1.default()
            .setName('activity')
            .setMethod(constants_1.HTTP_METHOD.POST)
            .setBody(data);
        if (callbacks) {
            send(request, callbacks);
        }
        else {
            return send(request);
        }
    }
    /**
     * Initializes drag and drop if dragDropEnabled = true.
     */
    function initDragDrop() {
        if (DRAGDROP_ENABLED) {
            var request = new request_1.default()
                .setName('initDragDrop')
                .setMethod(constants_1.HTTP_METHOD.GET);
            send(request).catch(function () { });
        }
    }
    /**
     * Triggers user's transfer listeners
     */
    function notifyTransferListeners(response) {
        // First update the iterate token for future requests
        transferEventIterationToken = response.iteration_token;
        // Notify the listeners
        for (var i = 0; i < transferListeners.length; i++) {
            transferListeners[i](constants_1.EVENT.TRANSFER, response);
        }
    }
    function pollTransfersHelperFunction() {
        // TODO: Need to make sure that all request implementations error on timeout
        if (outstandingActivityReqs >= MAX_ACTIVITY_OUTSTANDING) {
            Logger.debug('Skipping activity request. Reached maximum number of outstanding polling requests.');
            return;
        }
        outstandingActivityReqs++;
        getAllTransfersHelper(transferEventIterationToken, {
            success: function (response) {
                outstandingActivityReqs--;
                notifyTransferListeners(response);
            },
            error: function () {
                outstandingActivityReqs--;
            }
        });
    }
    /**
     * Removes user's event listeners
     */
    function removeEventListenerHelper(listener, listenerArray) {
        var listenerFound = false;
        var index = listenerArray.indexOf(listener);
        while (index > -1) {
            listenerArray.splice(index, 1);
            listenerFound = true;
            index = listenerArray.indexOf(listener);
        }
        return listenerFound;
    }
    ////////////////////////////////////////////////////////////////////////////
    // Manage Connect Status and high level logic
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Triggers user's status listeners
     */
    function notifyStatusListeners(notifyStatus) {
        for (var i = 0; i < statusListeners.length; i++) {
            statusListeners[i](constants_1.EVENT.STATUS, notifyStatus);
        }
    }
    /**
     * Sets the global Connect status
     */
    function setConnectStatus(newStatus) {
        /** Avoid handling redundant status updates */
        if (connectStatus === newStatus) {
            return;
        }
        Logger.debug('[' + objectId + '] Connect status changing from[' + connectStatus + '] to[' + newStatus + ']');
        /**
         * Handle case where Connect goes to running outside of normal init sequence.
         * For example, during upgrade.
         */
        if (newStatus === constants_1.STATUS.RUNNING) {
            connectReady();
        }
        connectStatus = newStatus;
    }
    function manageConnectStatus(newStatus) {
        if (newStatus === constants_1.STATUS.INITIALIZING) {
            setConnectStatus(constants_1.STATUS.INITIALIZING);
        }
        else if (newStatus === constants_1.STATUS.RETRYING) {
            setConnectStatus(constants_1.STATUS.RETRYING);
        }
        else if (newStatus === constants_1.STATUS.FAILED) {
            setConnectStatus(constants_1.STATUS.FAILED);
        }
        else if (newStatus === constants_1.STATUS.EXTENSION_INSTALL) {
            setConnectStatus(constants_1.STATUS.EXTENSION_INSTALL);
        }
        else if (newStatus === constants_1.STATUS.WAITING) {
            // No change
        }
        else if (newStatus === constants_1.STATUS.OUTDATED) {
            if (connectStatus !== constants_1.STATUS.OUTDATED) {
                setConnectStatus(constants_1.STATUS.OUTDATED);
            }
        }
        else if (newStatus === constants_1.STATUS.DEGRADED) {
            /** Should not get here. */
            return;
        }
        else {
            Logger.debug('Resetting max activity outstanding.');
            outstandingActivityReqs = 0;
            setConnectStatus(constants_1.STATUS.RUNNING);
        }
        notifyStatusListeners(connectStatus);
    }
    function send(request, callbacks) {
        if (!apiReady) {
            // Should wait for the RUNNING status event before using the Connect API
            Logger.warn('Connect API is not yet available - wait for the running status event.');
        }
        else if (!api) {
            throw new Error('Must call #initSession before using the Connect API.');
        }
        /** Add default settings for all POST requests */
        if (request.method === constants_1.HTTP_METHOD.POST) {
            request.addSettings(addStandardSettings);
        }
        if (callbacks) {
            request.send(api).then(function (response) {
                if (typeof callbacks.success === 'function') {
                    callbacks.success(response);
                }
            }).catch(function (error) {
                if (typeof callbacks.error === 'function') {
                    Logger.debug('Calling error callback.');
                    callbacks.error(error);
                }
            });
        }
        else {
            return request.send(api);
        }
    }
    /**
     * @function
     * @name AW4.Connect#addEventListener
     * @description Subscribe for Connect events. The first time the listener is
     *   called it will receive an event for each of the transfers already displayed
     *   in Connect, such that the listener will know the complete state of all transfers.
     * @param {EVENT} type The type of event to receive events for.
     * @param {Function} listener The function that will be called when the event occurs.
     *   Format:
     *   ```
     *   function(eventType, data) { ... }
     *   ```
     *   "transfer" event types return data format: {@link AllTransfersInfo}
     * @returns {null|Error}
     *
     * @example
     * // create a transfer listener
     * function transferListener(type, allTransfersInfo) {
     *   if (type === AW4.Connect.EVENT.TRANSFER) {
     *     console.log('Received transfer event!')
     *     handleTransferEvent(allTransfersInfo) // do something with the transfers data
     *   }
     * }
     * asperaWeb.addEventListener(AW4.Connect.EVENT.TRANSFER, transferListener)
     */
    this.addEventListener = function (type, listener) {
        // Check the parameters
        if (typeof type !== typeof constants_1.EVENT.ALL) {
            return Utils.createError(-1, 'Invalid EVENT parameter');
        }
        else if (typeof listener !== 'function') {
            return Utils.createError(-1, 'Invalid Listener parameter');
        }
        // Add the listener
        if (type === constants_1.EVENT.TRANSFER || type === constants_1.EVENT.ALL) {
            if (transferEventIntervalId === 0) {
                transferEventIntervalId = setInterval(pollTransfersHelperFunction, POLLING_TIME);
            }
            // Already set a function for polling the status, just add to the queue
            transferListeners.push(listener);
        }
        if (type === constants_1.EVENT.STATUS || type === constants_1.EVENT.ALL) {
            statusListeners.push(listener);
        }
    };
    function authenticate(authSpec, callbacks) {
        var request = new request_1.default()
            .setName('authenticate')
            .setMethod(constants_1.HTTP_METHOD.POST)
            .setBody(authSpec)
            .setValidator(validators_1.validateAuthSpec);
        if (callbacks) {
            send(request, callbacks);
        }
        else {
            return send(request);
        }
    }
    /**
     * Test authentication credentials against a transfer server.
     *
     * *This method is asynchronous.*
     *
     * @function
     * @name AW4.Connect#authenticate
     * @param {Object} authSpec Authentication credentials.
     *
     *  Options for `authSpec` (subset of {@link TransferSpec}):
     *  * `remote_host`
     *  * `ssh_port`
     *  * `remote_user`
     *  * `remote_password`
     *  * `token`
     *
     * @param  {Callbacks} callbacks `success` and `error` functions to receive results.
     *
     * Object returned to success callback:
     * `{}`
     * @return {null|Error}
     */
    this.authenticate = authenticate;
    function getAllTransfers(callbacks, iterationToken) {
        if (iterationToken === void 0) { iterationToken = 0; }
        return getAllTransfersHelper(iterationToken, callbacks);
    }
    /**
     * Get statistics for all transfers.
     *
     * *This method is asynchronous.*
     *
     * @function
     * @name AW4.Connect#getAllTransfers
     * @param {Callbacks} callbacks `success` and `error` functions to receive
     *   results.
     *
     *   Object returned to success callback:
     *   `{@link AllTransfersInfo}`
     * @param {String} [iterationToken='0'] If specified, return only transfers that have
     *   had activity since the last call.
     * @return {null}
     */
    this.getAllTransfers = getAllTransfers;
    /**
     * Get current status of Connect.
     *
     * @function
     * @name AW4.Connect#getStatus
     * @return {STATUS}
     */
    this.getStatus = function () {
        return connectStatus;
    };
    function getTransfer(transferId, callbacks) {
        var request = new request_1.default()
            .setName('getTransfer')
            .setMethod(constants_1.HTTP_METHOD.POST)
            .setParam(transferId)
            .setValidator(validators_1.validateTransferId);
        if (callbacks) {
            send(request, callbacks);
        }
        else {
            return send(request);
        }
    }
    /**
     * Get statistics for a single transfer.
     *
     * @function
     * @name AW4.Connect#getTransfer
     * @param {String} transferId The ID (`uuid`) of the transfer to retrieve.
     * @param {Callbacks} callbacks `success` and `error` functions to receive
     *  results.
     *
     *  Object returned to success callback:
     *  See `{@link TransferInfo}`
     *  ```
     *  {
     *   "transfer_info": TransferInfo
     *  }
     * @return {null}
     */
    this.getTransfer = getTransfer;
    /**
     * Call this method after creating the {@link AW4.Connect} object. It is mandatory to call this
     * function before making use of any other function of the API. If called more than
     * once on the same instance, it will return an error.
     *
     * Return format:
     * ```
     * {
     *  "app_id": "MjY2ZTM0YWItMGM2NC00ODdhLWJkNzQtNzU0YzVjN2FjYjJj"
     * }
     * ```
     *
     * @function
     * @name AW4.Connect#initSession
     * @param  {String} [applicationId] An ID to represent this session. Transfers
     *   initiated during this session will be associated with the ID. To continue
     *   a previous session, use the same ID as before. Use a unique ID in order to
     *   keep transfer information private from other websites. IF not specified,
     *   an ID is automatically generated for you.
     *
     * @returns {Object}
     */
    this.initSession = function (id) {
        if (id === void 0) { id = ''; }
        if (!Utils.isNullOrUndefinedOrEmpty(APPLICATION_ID)) {
            return Utils.createError(-1, 'Session was already initialized.');
        }
        if (!Utils.isNullOrUndefinedOrEmpty(id)) {
            APPLICATION_ID = id;
        }
        else {
            var appId = Utils.getLocalStorage(constants_1.LS_CONNECT_APP_ID);
            /** Generate a new application id */
            if (!appId) {
                appId = Utils.utoa(Utils.generateUuid());
            }
            APPLICATION_ID = appId;
        }
        Utils.setLocalStorage(constants_1.LS_CONNECT_APP_ID, APPLICATION_ID);
        if (!Utils.entropyOk(APPLICATION_ID)) {
            Logger.warn('WARNING: app_id field entropy might be too low.');
        }
        /** Initialize requests */
        var error = this.start();
        if (error && Utils.isError(error)) {
            return Utils.createError(-1, error);
        }
        return { 'app_id': APPLICATION_ID };
    };
    function modifyTransfer(transferId, options, callbacks) {
        var request = new request_1.default()
            .setName('modifyTransfer')
            .setMethod(constants_1.HTTP_METHOD.POST)
            .setParam(transferId)
            .setBody(options)
            .setValidator(validators_1.validateTransferId);
        if (callbacks) {
            send(request, callbacks);
        }
        else {
            return send(request);
        }
    }
    /**
     * Change the speed of a running transfer.
     *
     * *This method is asynchronous.*
     *
     * @function
     * @name AW4.Connect#modifyTransfer
     * @param {String} transferId The ID of the transfer to modify
     * @param {Object} options A subset of {@link TransferSpec}
     *
     * Options:
     * * `rate_policy`
     * * `target_rate_kbps`
     * * `min_rate_kbps`
     * * `target_rate_cap_kbps`
     * * `lock_rate_policy`
     * * `lock_target_rate`
     * * `lock_min_rate`
     * @param {Callbacks} callbacks `success` and `error` functions to receive results.
     *
     * Object returned to success callback:
     * `{@link TransferSpec}`
     * @return {null}
     */
    this.modifyTransfer = modifyTransfer;
    function readAsArrayBuffer(options, callbacks) {
        var request = new request_1.default()
            .setName('readAsArrayBuffer')
            .setMethod(constants_1.HTTP_METHOD.POST)
            .setBody(options)
            .setValidator(validators_1.validateArrayBufferOptions);
        if (callbacks) {
            send(request, callbacks);
        }
        else {
            return send(request);
        }
    }
    /**
     * Read file as 64-bit encoded data.
     *
     * *This method is asynchronous.*
     *
     * @function
     * @name AW4.Connect#readAsArrayBuffer
     * @param {Object} options Object with options needed for reading the file.
     *
     * Options:
     * * `path` (String) - Absolute path to the file we want to read.
     * @param {Callbacks} callbacks `success` and `error` functions to receive
     * results.
     *
     * Object returned to success callback:
     * ```
     * {
     *   "type" : "image/pjpeg",
     *   "data" : "/9j/4AAQSkZ..."
     * }
     * ```
     * @return {null|Error}
     */
    this.readAsArrayBuffer = readAsArrayBuffer;
    function readChunkAsArrayBuffer(options, callbacks) {
        var request = new request_1.default()
            .setName('readChunkAsArrayBuffer')
            .setMethod(constants_1.HTTP_METHOD.POST)
            .setBody(options)
            .setValidator(validators_1.validateBufferOptions);
        if (callbacks) {
            send(request, callbacks);
        }
        else {
            return send(request);
        }
    }
    /**
     * Read 64-bit encoded chunk from file.
     *
     * *This method is asynchronous.*
     *
     * @function
     * @name AW4.Connect#readChunkAsArrayBuffer
     * @param {Object} options Object with options needed for reading a chunk.
     *
     * Options:
     * * `path` (String) - Absolute path to the file we want to read the chunk from.
     * * `offset` (Number) - Offset (in bytes) that we want to start reading the file.
     * * `chunkSize` (Number) - The size (in bytes) of the chunk we want.
     * @param {Callbacks} callbacks `success` and `error` functions to receive
     * results.
     *
     * Object returned to success callback:
     * ```
     * {
     *   "type" : "image/pjpeg",
     *   "data" : "/9j/4AAQSkZ..."
     * }
     * ```
     * @return {null|Error}
     */
    this.readChunkAsArrayBuffer = readChunkAsArrayBuffer;
    function getChecksum(options, callbacks) {
        if (!options) {
            throw new Error('#getChecksum options argument is either missing or incorrect');
        }
        var localOptions = {
            path: options.path,
            offset: options.offset || 0,
            chunkSize: options.chunkSize || 0,
            checksumMethod: options.checksumMethod || 'md5'
        };
        var request = new request_1.default()
            .setName('getChecksum')
            .setMethod(constants_1.HTTP_METHOD.POST)
            .setBody(localOptions)
            .setValidator(validators_1.validateChecksumOptions);
        if (callbacks) {
            send(request, callbacks);
        }
        else {
            return send(request);
        }
    }
    /**
     * Calculates checksum of the given chunk size of the file.
     *
     * *This method is asynchronous.*
     *
     * @function
     * @name AW4.Connect#getChecksum
     * @param {Object} options Object with options needed for reading a chunk.
     *
     * Options:
     * * `path` (String) - Absolute path to the file we want to read the chunk from.
     * * `offset` (Number) - Offset (in bytes) that we want to start reading the file.
     * * `chunkSize` (Number) - The size (in bytes) of the chunk we want.
     * * `checksumMethod` (String) - The hash method we want to apply on chunk. Allowed checksum methods are "md5", "sha1", "sha256", "sha512".
     * @param {Callbacks} callbacks `success` and `error` functions to receive
     * results.
     *
     * Object returned to success callback:
     * ```
     * {
     *   "checksumMethod" : "md5"
     *   "checksum" : "35cf801a..."
     * }
     * ```
     * @return {null}
     */
    this.getChecksum = getChecksum;
    /**
     * Unsubscribe from Aspera Web events. If `type` is not specified,
     * all versions of the `listener` with different types will be removed.
     * If `listener` is not specified, all listeners for the `type` will be
     * removed. If neither `type` nor `listener` are specified, all listeners
     * will be removed.
     *
     * Return values:
     * * `true` - If we could find a listener with the parameters provided.
     * * `false` - If we could not find a listener for the parameters provided.
     *
     * @function
     * @name AW4.Connect#removeEventListener
     * @param {EVENT} [type] The type of event to stop receiving events for.
     * @param {Function} [listener] The function used to subscribe in {@link AW4.Connect#addEventListener}
     * @return {Boolean}
     */
    this.removeEventListener = function (type, listener) {
        var listenerFound = false;
        if (typeof type === 'undefined') {
            if (transferListeners.length > 0) {
                transferListeners = [];
                listenerFound = true;
            }
            if (statusListeners.length > 0) {
                statusListeners = [];
                listenerFound = true;
            }
        }
        else if (typeof type !== typeof constants_1.EVENT.ALL) {
            // The parameter type is actually the listener
            // @ts-ignore
            listenerFound = listenerFound || removeEventListenerHelper(type, transferListeners);
            // @ts-ignore
            listenerFound = listenerFound || removeEventListenerHelper(type, statusListeners);
        }
        else if (typeof listener !== 'function') {
            // The user only provided the type
            // First the TRANSFER events
            if (type === constants_1.EVENT.TRANSFER || type === constants_1.EVENT.ALL) {
                if (transferListeners.length > 0) {
                    transferListeners = [];
                    listenerFound = true;
                }
            }
            // Then the STATUS events
            if (type === constants_1.EVENT.STATUS || type === constants_1.EVENT.ALL) {
                if (statusListeners.length > 0) {
                    statusListeners = [];
                    listenerFound = true;
                }
            }
        }
        else {
            // The user provided both arguments
            // First the TRANSFER events
            if (type === constants_1.EVENT.TRANSFER || type === constants_1.EVENT.ALL) {
                listenerFound = listenerFound || removeEventListenerHelper(listener, transferListeners);
            }
            // Then the STATUS events
            if (type === constants_1.EVENT.STATUS || type === constants_1.EVENT.ALL) {
                listenerFound = listenerFound || removeEventListenerHelper(listener, statusListeners);
            }
        }
        if (transferListeners.length === 0) {
            clearInterval(transferEventIntervalId);
            transferEventIntervalId = 0;
        }
        return listenerFound;
    };
    function removeTransfer(transferId, callbacks) {
        var request = new request_1.default()
            .setName('removeTransfer')
            .setMethod(constants_1.HTTP_METHOD.POST)
            .setParam(transferId)
            .setValidator(validators_1.validateTransferId);
        if (callbacks) {
            send(request, callbacks);
        }
        else {
            return send(request);
        }
    }
    /**
     * Remove the transfer - terminating it if necessary - from Connect.
     *
     * *This method is asynchronous.*
     *
     * @function
     * @name AW4.Connect#removeTransfer
     * @param {String} transferId The ID(`uuid`) of the transfer to delete.
     * @param {Callbacks} callbacks `success` and `error` functions to receive
     *   results.
     *
     *   Object returned to success callback:
     *   `{@link TransferSpec}`
     * @return {null}
     */
    this.removeTransfer = removeTransfer;
    function resumeTransfer(transferId, options, callbacks) {
        var request = new request_1.default()
            .setName('resumeTransfer')
            .setMethod(constants_1.HTTP_METHOD.POST)
            .setParam(transferId)
            .setBody(options)
            .setValidator(validators_1.validateTransferId);
        if (callbacks) {
            send(request, callbacks);
        }
        else {
            return send(request);
        }
    }
    /**
     * Resume a transfer that was stopped.
     *
     * *This method is asynchronous.*
     *
     * @function
     * @name AW4.Connect#resumeTransfer
     * @param {String} transferId The ID(`uuid`) of the transfer to resume
     * @param {Object} options A subset of {@link TransferSpec}
     *
     * Options:
     * * `token`
     * * `cookie`
     * * `authentication`
     * * `remote_user`
     * * `remote_password`
     * * `content_protection_passphrase`
     * @param {Callbacks} callbacks `success` and `error` functions to receive results.
     *
     * Object returned to success callback:
     * `{@link TransferSpec}`
     * @return {null}
     */
    this.resumeTransfer = resumeTransfer;
    /**
     * Sets drag and drop options for the element given in the cssSelector. Please note that
     * the `dragDropEnabled` option must have been set to `true` when creating the {@link AW4.Connect}
     * object.
     *
     * *This method is asynchronous.*
     *
     * @function
     * @name AW4.Connect#setDragDropTargets
     * @param {String} cssSelector CSS selector for drop targets.
     * @param {Object} [options] Drag and drop options for these targets.
     *
     *  Options:
     *  * `dragEnter` (Boolean) - `true` if drag enter event should trigger the listener. Default: `false`.
     *  * `dragOver` (Boolean) - `true` if drag over event should trigger the listener. Default: `false`.
     *  * `dragLeave` (Boolean) - `true` if drag leave event should trigger the listener. Default: `false`.
     *  * `drop` (Boolean) - `true` if drop event should trigger the listener. Default: `true`.
     *  * `allowPropagation` (Boolean) - `true` allow further propagation of events. Default: `false`.
     * @param {Function} listener Function to be called when each of the events occurs.
     *
     *   Format:
     *   ```
     *   function(event, files) { ... }
     *   ```
     *   * `event` (Object) - DOM Event object as implemented by the browser.
     *   * `files` (Object) - See {@link dataTransfer}. This is only valid on `drop` events.
     * @return {null|Error}
     */
    this.setDragDropTargets = function (cssSelector, options, listener) {
        if (!DRAGDROP_ENABLED) {
            return Utils.createError(-1, 'Drop is not enabled in the initialization ' +
                'options, please instantiate Connect again with the dragDropEnabled option set to true.');
        }
        if (typeof listener !== 'function') {
            return Utils.createError(-1, 'You must provide a valid listener');
        }
        if (Utils.isNullOrUndefinedOrEmpty(options)) {
            return Utils.createError(-1, 'You must provide a valid options object');
        }
        var elements = document.querySelectorAll(cssSelector);
        if (elements.length === 0) {
            return Utils.createError(-1, 'No valid elements for the selector given');
        }
        var dragListener = function (evt) {
            if (!options.allowPropagation) {
                evt.stopPropagation();
            }
            evt.preventDefault();
            listener({ event: evt });
        };
        // Needed for the Drop event to be called
        var dragOverListener = function (evt) {
            if (!options.allowPropagation) {
                evt.stopPropagation();
            }
            evt.preventDefault();
            if (options.dragOver === true) {
                listener({ event: evt });
            }
        };
        var dropListener = function (evt) {
            if (!options.allowPropagation) {
                evt.stopPropagation();
            }
            evt.preventDefault();
            // Prepare request and create a valid JSON object to be serialized
            var filesDropped = evt.dataTransfer.files;
            var data = {};
            data.dataTransfer = {};
            data.dataTransfer.files = [];
            for (var i = 0; i < filesDropped.length; i++) {
                var fileObject = {
                    'lastModifiedDate': filesDropped[i].lastModifiedDate,
                    'name': filesDropped[i].name,
                    'size': filesDropped[i].size,
                    'type': filesDropped[i].type
                };
                data.dataTransfer.files.push(fileObject);
            }
            // Drop helper
            var dropHelper = function (response) {
                listener({ event: evt, files: response });
            };
            var request = new request_1.default()
                .setName('droppedFiles')
                .setMethod(constants_1.HTTP_METHOD.POST)
                .setBody(data);
            send(request, {
                success: dropHelper
            });
        };
        for (var i = 0; i < elements.length; i++) {
            // Independent from our implementation
            if (options.dragEnter === true) {
                elements[i].addEventListener('dragenter', dragListener);
            }
            if (options.dragLeave === true) {
                elements[i].addEventListener('dragleave', dragListener);
            }
            if (options.dragOver === true || options.drop !== false) {
                elements[i].addEventListener('dragover', dragOverListener);
            }
            if (options.drop !== false) {
                elements[i].addEventListener('drop', dropListener);
            }
        }
    };
    function showAbout(callbacks) {
        var request = new request_1.default()
            .setName('showAbout')
            .setMethod(constants_1.HTTP_METHOD.GET);
        if (callbacks) {
            send(request, callbacks);
        }
        else {
            return send(request);
        }
    }
    /**
     * Displays the IBM Aspera Connect "About" window.
     *
     * *This method is asynchronous.*
     *
     * @function
     * @name AW4.Connect#showAbout
     * @param {Callbacks} callbacks `success` and `error` functions to receive
     *   results.
     *
     *   Object returned to success callback:
     *   `{}`
     * @return {null}
     */
    this.showAbout = showAbout;
    function showDirectory(transferId, callbacks) {
        var request = new request_1.default()
            .setName('showDirectory')
            .setMethod(constants_1.HTTP_METHOD.GET)
            .setParam(transferId)
            .setValidator(validators_1.validateTransferId);
        if (callbacks) {
            send(request, callbacks);
        }
        else {
            return send(request);
        }
    }
    /**
     * Open the destination directory of the transfer using the system file
     * browser.
     *
     * *This method is asynchronous.*
     *
     * @function
     * @name AW4.Connect#showDirectory
     * @param {String} transferId The ID(`uuid`) of the transfer to show files for.
     * @param {Callbacks} callbacks `success` and `error` functions to receive
     *   results.
     *
     *   Object returned to success callback:
     *   `{}`
     * @return {null}
     */
    this.showDirectory = showDirectory;
    function showPreferences(callbacks) {
        var request = new request_1.default()
            .setName('showPreferences')
            .setMethod(constants_1.HTTP_METHOD.GET);
        if (callbacks) {
            send(request, callbacks);
        }
        else {
            return send(request);
        }
    }
    /**
     * Displays the IBM Aspera Connect "Preferences" window.
     *
     * *This method is asynchronous.*
     *
     * @function
     * @name AW4.Connect#showPreferences
     * @param {Callbacks} callbacks `success` and `error` functions to receive
     *   results.
     *
     *   Object returned to success callback:
     *   `{}`
     * @return {null}
     */
    this.showPreferences = showPreferences;
    function showPreferencesPage(options, callbacks) {
        var allowedPages = ['general', 'transfers', 'bandwidth', 'network', 'security'];
        if (options && options.page && allowedPages.indexOf(options.page) > -1) {
            var request = new request_1.default()
                .setName('showPreferencesPage')
                .setMethod(constants_1.HTTP_METHOD.GET)
                .setParam(options.page);
            if (callbacks) {
                send(request, callbacks);
            }
            else {
                return send(request);
            }
        }
        else {
            throw new Error('#showPreferencesPage options argument is either missing or incorrect.');
        }
    }
    /**
     * Displays the IBM Aspera Connect "Preferences" window opened to a specifiic page.
     *
     * *This method is asynchronous*
     *
     * @function
     * @name AW4.Connect#showPreferencesPage
     * @param {options} options Options used when opening preferences.
     *
     * Options:
     * * `page` (String) - `general`, `transfers`, `network`, `bandwidth`, `security`
     * @param {Callbacks} callbacks `success` and `error` functions to receive results.
     *
     *  Object returned to success callback:
     *  `{}`
     * @return {null}
     */
    this.showPreferencesPage = showPreferencesPage;
    /**
     * Displays a file chooser dialog for the user to pick a "save-to" path.
     *
     * *This method is asynchronous.*
     *
     * @function
     * @name AW4.Connect#showSaveFileDialog
     * @param {Callbacks} callbacks `success` and `error` functions to receive
     *   results.
     *
     * Object returned to success callback:
     * See `{@link dataTransfer}`.
     * @param {Object} [options] File chooser options
     *
     * Options:
     * * `allowedFileTypes` ({@link FileFilters}) - Filter the files displayed by file extension.
     * * `suggestedName` (String) - The file name to pre-fill the dialog with.
     * * `title` (String) - The name of the dialog window.
     * @return {null|Error}
     */
    this.showSaveFileDialog = function (callbacks, options) {
        var localOptions = {};
        if (Utils.isNullOrUndefinedOrEmpty(options)) {
            options = {};
        }
        localOptions.title = options.title || '';
        localOptions.suggestedName = options.suggestedName || '';
        localOptions.allowedFileTypes = options.allowedFileTypes || '';
        var request = new request_1.default()
            .setName('showSaveFileDialog')
            .setMethod(constants_1.HTTP_METHOD.POST)
            .setBody(localOptions);
        if (callbacks) {
            send(request, callbacks);
        }
        else {
            throw new Error('Must provide callbacks.');
        }
    };
    /**
     * Displays a file browser dialog for the user to select files. The select file
     * dialog call(s) may be separated in time from the later startTransfer(s) call,
     * but they must occur in the same Connect session.
     *
     * *This method is asynchronous.*
     *
     * @function
     * @name AW4.Connect#showSelectFileDialog
     * @param {Callbacks} callbacks `success` and `error` functions to receive
     *   results.
     *
     * Object returned to success callback:
     * See `{@link dataTransfer}`.
     * @param {Object} [options] File chooser options
     *
     * Options:
     * * `allowedFileTypes` ({@link FileFilters}) - Filter the files displayed by file extension.
     * * `allowMultipleSelection` (Boolean) -  Allow the selection of multiple
     *    files. Default: `true`.
     * * `title` (String) - The name of the dialog window.
     * @return {null|Error}
     */
    this.showSelectFileDialog = function (callbacks, options) {
        var localOptions = {};
        if (Utils.isNullOrUndefinedOrEmpty(options)) {
            options = {};
        }
        localOptions.title = options.title || '';
        localOptions.suggestedName = options.suggestedName || '';
        localOptions.allowMultipleSelection = Utils.isNullOrUndefinedOrEmpty(options.allowMultipleSelection) || options.allowMultipleSelection;
        localOptions.allowedFileTypes = options.allowedFileTypes || '';
        var request = new request_1.default()
            .setName('showSelectFileDialog')
            .setMethod(constants_1.HTTP_METHOD.POST)
            .setBody(localOptions);
        if (callbacks) {
            send(request, callbacks);
        }
        else {
            throw new Error('Must provide callbacks.');
        }
    };
    /**
     * Displays a file browser dialog for the user to select files. The select file
     * dialog call(s) may be separated in time from the later startTransfer(s) call,
     * but they must occur in the same Connect session.
     *
     * @function
     * @name AW4.Connect#showSelectFileDialogPromise
     * @param {Object} [options] File chooser options
     *
     * Options:
     * * `allowedFileTypes` ({@link FileFilters}) - Filter the files displayed by file extension.
     * * `allowMultipleSelection` (Boolean) -  Allow the selection of multiple
     *    files. Default: `true`.
     * * `title` (String) - The name of the dialog window.
     * @return {Promise<dataTransfer>}
     */
    this.showSelectFileDialogPromise = function (options) {
        var localOptions = {};
        if (Utils.isNullOrUndefinedOrEmpty(options)) {
            options = {};
        }
        localOptions.title = options.title || '';
        localOptions.suggestedName = options.suggestedName || '';
        localOptions.allowMultipleSelection = Utils.isNullOrUndefinedOrEmpty(options.allowMultipleSelection) || options.allowMultipleSelection;
        localOptions.allowedFileTypes = options.allowedFileTypes || '';
        var request = new request_1.default()
            .setName('showSelectFileDialog')
            .setMethod(constants_1.HTTP_METHOD.POST)
            .setBody(localOptions);
        return send(request);
    };
    /**
     * Displays a file browser dialog for the user to select directories. The select
     * folder dialog call(s) may be separated in time from the later startTransfer(s)
     * call, but they must occur in the same Connect session.
     *
     * *This method is asynchronous.*
     *
     * @function
     * @name AW4.Connect#showSelectFolderDialog
     * @param {Callbacks} callbacks `success` and `error` functions to receive
     *   results.
     *
     * Object returned to success callback:
     * See `{@link dataTransfer}`.
     * @param {Object} [options] File chooser options
     *
     * Options:
     * * `allowMultipleSelection` (Boolean) -  Allow the selection of multiple
     *    folders. Default: `true`.
     * * `title` (String) - The name of the dialog window.
     * @return {null|Error}
     */
    this.showSelectFolderDialog = function (callbacks, options) {
        var localOptions = {};
        if (Utils.isNullOrUndefinedOrEmpty(options)) {
            options = {};
        }
        localOptions.title = options.title || '';
        localOptions.allowMultipleSelection = Utils.isNullOrUndefinedOrEmpty(options.allowMultipleSelection) || options.allowMultipleSelection;
        var request = new request_1.default()
            .setName('showSelectFolderDialog')
            .setMethod(constants_1.HTTP_METHOD.POST)
            .setBody(localOptions);
        if (callbacks) {
            send(request, callbacks);
        }
        else {
            throw new Error('Must provide callbacks.');
        }
    };
    /**
     * Displays a file browser dialog for the user to select directories. The select
     * folder dialog call(s) may be separated in time from the later startTransfer(s)
     * call, but they must occur in the same Connect session.
     *
     * @function
     * @name AW4.Connect#showSelectFolderDialogPromise
     * @param {Object} [options] File chooser options
     *
     * Options:
     * * `allowMultipleSelection` (Boolean) -  Allow the selection of multiple
     *    folders. Default: `true`.
     * * `title` (String) - The name of the dialog window.
     * @return {Promise<dataTransfer>}
     */
    this.showSelectFolderDialogPromise = function (options) {
        var localOptions = {};
        if (Utils.isNullOrUndefinedOrEmpty(options)) {
            options = {};
        }
        localOptions.title = options.title || '';
        localOptions.allowMultipleSelection = Utils.isNullOrUndefinedOrEmpty(options.allowMultipleSelection) || options.allowMultipleSelection;
        var request = new request_1.default()
            .setName('showSelectFolderDialog')
            .setMethod(constants_1.HTTP_METHOD.POST)
            .setBody(localOptions);
        return send(request);
    };
    function showTransferManager(callbacks) {
        var request = new request_1.default()
            .setName('showTransferManager')
            .setMethod(constants_1.HTTP_METHOD.GET);
        if (callbacks) {
            send(request, callbacks);
        }
        else {
            return send(request);
        }
    }
    /**
     * Displays the IBM Aspera Connect "Activity" window.
     *
     * *This method is asynchronous.*
     *
     * @function
     * @name AW4.Connect#showTransferManager
     * @param {Callbacks} callbacks `success` and `error` functions to receive
     *   results.
     *
     *   Object returned to success callback:
     *   `{}`
     * @return {null}
     */
    this.showTransferManager = showTransferManager;
    function showTransferMonitor(transferId, callbacks) {
        var request = new request_1.default()
            .setName('showTransferMonitor')
            .setMethod(constants_1.HTTP_METHOD.GET)
            .setParam(transferId)
            .setValidator(validators_1.validateTransferId);
        if (callbacks) {
            send(request, callbacks);
        }
        else {
            return send(request);
        }
    }
    /**
     * Displays the IBM Aspera Connect "Transfer Monitor" window for the transfer.
     *
     * *This method is asynchronous.*
     *
     * @function
     * @name AW4.Connect#showTransferMonitor
     * @param {String} transferId The ID(`uuid`) of the corresponding transfer.
     * @param {Callbacks} callbacks `success` and `error` functions to receive
     *   results.
     *
     *   Object returned to success callback:
     *   `{}`
     * @return {null}
     */
    this.showTransferMonitor = showTransferMonitor;
    /**
     * Start looking for Connect. Please note that this is called internally by {@link AW4.Connect#initSession}
     * and it should only be called directly after a call to {@link AW4.Connect#stop}.
     *
     * @function
     * @name AW4.Connect#start
     * @return {null|Error}
     */
    this.start = function () {
        if (Utils.isNullOrUndefinedOrEmpty(APPLICATION_ID)) {
            return Utils.createError(-1, 'Please call #initSession first.');
        }
        /** Initialize request handler and launch Connect */
        requestHandler.init().then(function () {
            Logger.debug("Initialization finished. Connect status: " + connectStatus);
            /** Make sure to mark Connect is ready if for some reason it's not already so we don't block requests */
            if (!apiReady && connectStatus === constants_1.STATUS.RUNNING) {
                connectReady();
            }
            if (connectStatus !== constants_1.STATUS.RUNNING) {
                Logger.debug('Connect API is not ready.');
            }
        }).catch(function (error) {
            Logger.error('Initialization error:', error);
        });
    };
    /**
     * Initiates a single transfer. Call {@link AW4.Connect#getAllTransfers} to get transfer
     * statistics, or register an event listener through {@link AW4.Connect#addEventListener}.
     *
     * Return format:
     * ```
     * {
     *  "request_id": "bb1b2e2f-3002-4913-a7b3-f7aef4e79132"
     * }
     * ```
     * The `request_id`, which is returned immediately, may be used for matching
     * this transfer with its events.
     *
     * @function
     * @name AW4.Connect#startTransfer
     * @param {TransferSpec} transferSpec Transfer parameters.
     * @param {ConnectSpec} connectSpec Connect options
     * @param {Callbacks} callbacks `success` and `error` functions to receive results.
     *   This call is successful if Connect is able to start the
     *   transfer. Note that an error could still occur after the transfer starts,
     *   e.g. if authentication fails. Use {@link AW4.Connect#addEventListener} to
     *   receive notifications about errors that occur during a transfer session.
     *   This call fails if validation fails or the user rejects the transfer.
     *
     * Object returned to success callback:
     * `{@link TransferSpecs}`
     *
     * @returns {Object|Error}
     */
    function startTransfer(transferSpec, asperaConnectSettings, callbacks) {
        if (Utils.isNullOrUndefinedOrEmpty(transferSpec)) {
            throw new Error('#startTransfer transferSpec is missing or invalid');
        }
        var settings = asperaConnectSettings || {};
        var localCallbacks = callbacks || {};
        var transferSpecs = {
            transfer_specs: [{
                    transfer_spec: transferSpec,
                    aspera_connect_settings: settings
                }]
        };
        return this.startTransfers(transferSpecs, localCallbacks);
    }
    this.startTransfer = startTransfer;
    /**
     * Initiates a single transfer. Call {@link AW4.Connect#getAllTransfers} to get transfer
     * statistics, or register an event listener through {@link AW4.Connect#addEventListener}.
     *
     * @function
     * @name AW4.Connect#startTransferPromise
     * @param {TransferSpec} transferSpec Transfer parameters.
     * @param {ConnectSpec} connectSpec Connect options
     *
     * @returns {Promise<TransferSpecs>}
     */
    function startTransferPromise(transferSpec, asperaConnectSettings) {
        if (Utils.isNullOrUndefinedOrEmpty(transferSpec)) {
            throw new Error('#startTransfer transferSpec is missing or invalid');
        }
        var settings = asperaConnectSettings || {};
        var transferSpecs = {
            transfer_specs: [{
                    transfer_spec: transferSpec,
                    aspera_connect_settings: settings
                }]
        };
        return this.startTransfers(transferSpecs);
    }
    this.startTransferPromise = startTransferPromise;
    function startTransfers(transferSpecs, callbacks) {
        var requestId = Utils.generateUuid();
        var request = new request_1.default()
            .setName('startTransfer')
            .setMethod(constants_1.HTTP_METHOD.POST)
            .setBody(transferSpecs)
            .setRequestId(requestId);
        if (callbacks) {
            send(request, callbacks);
            return { request_id: requestId };
        }
        else {
            return send(request);
        }
    }
    /**
     * Initiates one or more transfers (_currently only the first `transfer_spec`
     * is used_). It's recommended to instead use {@link AW4.Connect#startTransfer}. Call {@link AW4.Connect#getAllTransfers} to get transfer
     * statistics, or register an event listener through {@link AW4.Connect#addEventListener}.
     *
     * *This method is asynchronous.*
     *
     * Return format:
     * ```
     * {
     *  "request_id": "bb1b2e2f-3002-4913-a7b3-f7aef4e79132"
     * }
     * ```
     * The `request_id`, which is returned immediately, may be used for matching
     * this transfer with its events.
     *
     * @function
     * @name AW4.Connect#startTransfers
     * @param {Object} transferSpecs Transfer parameters.
     *
     * Format:
     * See {@link TransferSpecs}
     * @param {Callbacks} callbacks `success` and `error` functions to receive results.
     *   This call is successful if Connect is able to start the
     *   transfer. Note that an error could still occur after the transfer starts,
     *   e.g. if authentication fails. Use {@link AW4.Connect#addEventListener} to
     *   receive notifications about errors that occur during a transfer session.
     *   This call fails if validation fails or the user rejects the transfer.
     *
     * Object returned to success callback:
     * {@link TransferSpecs}
     *
     * @returns {Object|Error}
     */
    this.startTransfers = startTransfers;
    /**
     * Stop all requests from Connect to restart activity, please
     * create a new {@link AW4.Connect} object or call {@link AW4.Connect#start}.
     *
     * @function
     * @name AW4.Connect#stop
     * @return {Boolean}
     */
    this.stop = function () {
        return requestHandler.stopRequests();
    };
    function stopTransfer(transferId, callbacks) {
        var request = new request_1.default()
            .setName('stopTransfer')
            .setMethod(constants_1.HTTP_METHOD.POST)
            .setParam(transferId)
            .setValidator(validators_1.validateTransferId);
        if (callbacks) {
            send(request, callbacks);
        }
        else {
            return send(request);
        }
    }
    /**
     * Terminate the transfer. Use {@link AW4.Connect#resumeTransfer} to resume.
     *
     * *This method is asynchronous.*
     *
     * @function
     * @name AW4.Connect#stopTransfer
     * @param {String} transferId The ID(`uuid`) of the transfer to stop.
     * @param {Callbacks} callbacks `success` and `error` functions to receive
     *   results.
     *
     *   Object returned to success callback:
     *   `{}`
     * @return {null}
     */
    this.stopTransfer = stopTransfer;
    function testSshPorts(options, callbacks) {
        if (options && options.remote_host) {
            var localOptions = {};
            localOptions.remote_host = options.remote_host;
            localOptions.ssh_port = options.ssh_port || 33001;
            localOptions.timeout_sec = options.timeout_sec || 3;
            var request = new request_1.default()
                .setName('testSshPorts')
                .setMethod(constants_1.HTTP_METHOD.POST)
                .setBody(localOptions);
            if (callbacks) {
                send(request, callbacks);
            }
            else {
                return send(request);
            }
        }
        else {
            throw new Error('#testSshPorts options argument is either missing or incorrect.');
        }
    }
    /**
     * Test that Connect can open a TCP connection to `remote_host` over the given `ssh_port`.
     *
     * *This method is asynchronous.*
     *
     * @function
     * @name AW4.Connect#testSshPorts
     * @param {Object} options Test options.
     * Options:
     * * `remote_host` (String) - Domain name of the transfer server.
     * * `ssh_port` (Number) - SSH port. Default: `33001`.
     * * `timeout_sec` (Number) - Timeout value in seconds. Default: `3`.
     * @param {Callbacks} callbacks `success` and `error` functions to receive
     *   results.
     *
     *   Object returned to success callback:
     *   `{}`
     *
     *
     * @return {null}
     */
    this.testSshPorts = testSshPorts;
    function version(callbacks) {
        var request = new request_1.default()
            .setName('version')
            .setMethod(constants_1.HTTP_METHOD.GET);
        if (callbacks) {
            send(request, callbacks);
        }
        else {
            return send(request);
        }
    }
    /**
     * Get the IBM Aspera Connect version and installation context.
     *
     * *This method is asynchronous.*
     *
     * @function
     * @name AW4.Connect#version
     * @param {Callbacks} callbacks `success` and `error` functions to receive
     *   results.
     *
     *   Object returned to success callback:
     *   ```
     *   {
     *     "system_wide": false,
     *     "version": "3.9.1.171801"
     *   }
     *   ```
     * @return {null}
     */
    this.version = version;
};
/**
 * AW4.Connect.EVENT
 * @typedef {Object} EVENT
 * @property {string} ALL="all" all event
 * @property {string} TRANSFER="transfer" transfer event
 * @property {string} STATUS="status" status event
 * @example
 *
 * AW4.Connect.EVENT.ALL // returns "all"
 * AW4.Connect.EVENT.STATUS // returns "status"
 * AW4.Connect.EVENT.TRANSFER // returns "transfer"
 */
ConnectClient.EVENT = constants_1.EVENT;
ConnectClient.HTTP_METHOD = constants_1.HTTP_METHOD;
/**
 * AW4.Connect.STATUS
 * @typedef {Object} STATUS
 * @property {string} INITIALIZING="INITIALIZING" initializing status event
 * @property {string} RETRYING="RETRYING" retrying status event
 * @property {string} RUNNING="RUNNING" running status event
 * @property {string} OUTDATED="OUTDATED" outdated status event
 * @property {string} FAILED="FAILED" failed status event
 * @property {string} EXTENSION_INSTALL="EXTENSION_INSTALL" extension install event type
 * @example
 *
 * AW4.Connect.STATUS.INITIALIZING // returns "INITIALIZING"
 * AW4.Connect.STATUS.RETRYING // returns "RETRYING"
 * // etc...
 */
var localStatus = Utils.copyObject(constants_1.STATUS);
delete localStatus.DEGRADED;
delete localStatus.STOPPED;
delete localStatus.WAITING;
ConnectClient.STATUS = localStatus;
/**
 * AW4.Connect.TRANSFER_STATUS
 *
 * The possible states of a transfer reported by`status` in {@link TransferInfo}.
 * @typedef {Object} TRANSFER_STATUS
 * @property {String} CANCELLED="cancelled" The user stopped the transfer.
 * @property {String} COMPLETED="completed" The transfer finished successfully.
 * @property {String} FAILED="failed" The transfer had an error.
 * @property {String} INITIATING="initiating" The transfer reqeust was accepted. Now
 *   starting transfer.
 * @property {String} QUEUED="queued" The transfer is waiting for other transfers to finish.
 *   The queue is configurable in Connect.
 * @property {String} REMOVED="removed" The user deleted the transfer.
 * @property {String} RUNNING="running" Transfer in progress.
 * @property {String} WILLRETRY="willretry" Transfer waiting to retry after a
 *   recoverable error.
 */
ConnectClient.TRANSFER_STATUS = constants_1.TRANSFER_STATUS;
exports.default = ConnectClient;
/**
 * The data format for statistics for all existing transfers.
 * See {@link TransferInfo}
 *
 * @typedef {Object} AllTransfersInfo
 * @property {Number} iteration_token=0 A marker that represents the moment in time
 *   that the transfer status was retrieved. If it is passed as an argument to
 *   a {@link AW4.Connect#getAllTransfers} call, the response returned will only contain transfers
 *   that have had activity since the previous call. Note that this token persists
 *   even if the user restarts Connect.
 * @property {Number} result_count=0 The number of {@link TransferInfo} objects returned
 *   {@link AllTransfersInfo.transfers}.
 * @property {Array} transfers An array that contains {@link TransferInfo} objects.
 *
 * @example
 * {
 *  "iteration_token": 28,
 *  "result_count": 3,
 *  "transfers": [
 *    {@link TransferInfo},
 *    {@link TransferInfo},
 *    {@link TransferInfo}
 *   ]
 * }
 */
/**
 * The data format for statistics for on transfer session.
 *
 * See {@link TransferSpec} and {@link AsperaConnectSettings} for definitions.
 *
 * @typedef {Object} TransferInfo
 * @property {String} add_time The time when the transfer was added (according
 *   to the system's clock).
 * @property {Object} aspera_connect_settings {@link AsperaConnectSettings}
 * @property {Number} bytes_expected The number of bytes that are still
 *   remaining to be written.
 * @property {Number} bytes_written The number of bytes that have already been
 *   written to disk.
 * @property {Number} calculated_rate_kbps The current rate of the transfer in kbps.
 * @property {String} current_file The full path of the current file.
 * @property {Number} elapsed_usec The duration in microseconds of the transfer since it started
 *   transferring.
 * @property {String} explorer_path The path opened in Explorer/Finder when user clicks
 *   'Open Containing Folder' in Connect's Activity window.
 * @property {String} end_time The time when the transfer was completed.
 * @property {Object} file_counts A running aggregate count of files in the transfer session
 *   that have already been processed with information about the number of files attempted,
 *   completed, failed, and skipped. Note: "completed" includes the number of files
 *   transferred or skipped.
 *
 *   Format:
 *   ```
 *   {
 *     "attempted": 2,
 *     "completed": 2,
 *     "failed": 0,
 *     "skipped": 1
 *   }
 *   ```
 * @property {Array} files A list of files that have been active in this
 *   transfer session. Note that files that have not been active yet in this session
 *   will not be reported (and you can assume bytes_written is 0).
 *
 *   Format:
 *   ```
 *   [
 *     {
 *       "bytes_expected": 10485760,
 *       "bytes_written": 1523456,
 *       "fasp_file_id": "3c40b511-5b2dfebb-a2e63483-9b58cb45-9cd9abff",
 *       "file": "/Users/aspera/Downloads/connect_downloads/10MB.3"
 *     }, {
 *       "bytes_expected": 10485760,
 *       "bytes_written": 10485760,
 *       "fasp_file_id": "d5b7deea-2d5878f4-222661f6-170ce0f2-68880a6c",
 *       "file": "/Users/aspera/Downloads/connect_downloads/10MB.2"
 *     }
 *   ]
 *   ```
 * @property {String} modify_time The last time the transfer was modified
 * @property {Number} percentage The progress of the transfer over 1.
 * @property {String} previous_status The previous status of the transfer.
 * @property {Number} remaining_usec The ETA of the transfer in microseconds.
 * @property {String} start_time The time when the transfer moved to initiating
 *   status.
 * @property {String} status The status of the transfer. See {@link TRANSFER_STATUS}.
 * @property {String} title The name of the file.
 * @property {Number} transfer_iteration_token A marker that represents the moment
 *   in time that the transfer status was checked.
 * @property {Object} transfer_spec {@link TransferSpec}
 * @property {"fasp"|"http"} transport="fasp" `fasp` - (default) <br>
 *   `http` - Set when a fasp transfer could not be performed and http fallback was used.
 * @property {String} uuid
 *
 * @example
 *     {
 *       "add_time": "2012-10-05T17:53:16",
 *       "aspera_connect_settings": {@link AsperaConnectSettings},
 *       "bytes_expected": 102400,
 *       "bytes_written": 11616,
 *       "calculated_rate_kbps": 34,
 *       "current_file": "/temp/tinyfile0001",
 *       "elapsed_usec": 3000000,
 *       "explorer_path": "/Users/aspera/Downloads/connect_downloads/10MB.3",
 *       "end_time": "",
 *       "file_counts": {
 *           "attempted": 1,
 *           "completed": 1,
 *           "failed": 0,
 *           "skipped": 1
 *       },
 *       "files": [
 *          {
 *            "bytes_expected": 10485760,
 *            "bytes_written": 1523456,
 *            "fasp_file_id": "3c40b511-5b2dfebb-a2e63483-9b58cb45-9cd9abff",
 *            "file": "/Users/aspera/Downloads/connect_downloads/10MB.3"
 *          }, {
 *            "bytes_expected": 10485760,
 *            "bytes_written": 10485760,
 *            "fasp_file_id": "d5b7deea-2d5878f4-222661f6-170ce0f2-68880a6c",
 *            "file": "/Users/aspera/Downloads/connect_downloads/10MB.2"
 *          }
 *       ],
 *       "modify_time": "2012-10-05T17:53:18",
 *       "percentage": 0.113438,
 *       "previous_status": "initiating",
 *       "remaining_usec": 21000000,
 *       "start_time": "2012-10-05T17:53:16",
 *       "status": "running",
 *       "title": "tinyfile0001",
 *       "transfer_iteration_token": 18,
 *       "transfer_spec": {@link TransferSpec},
 *       "transport": "fasp",
 *       "uuid": "add433a8-c99b-4e3a-8fc0-4c7a24284ada",
 *     }
 */
/**
 * The response returned to the {@link AW4.Connect#startTransfer} success callback.
 *
 * @typedef {Object} TransferSpecs
 * @property {Array} transfer_specs An array that contains {@link TransferSpec} and
 *   {@link ConnectSpec} objects.
 *
 * @example
 * {
 *  "transfer_specs": [
 *     {
 *        "transfer_spec": {@link TransferSpec},
 *        "aspera_connect_settings": {@link ConnectSpec}
 *     }
 *   ]
 * }
 */
/**
 * The parameters for starting a transfer.
 *
 * @typedef {Object} TransferSpec
 *
 * @property {"password"|"token"} [authentication="password"] The type of authentication to use.
 * @property {"none"|"aes-128"} [cipher="aes-128"] The algorithm used to encrypt
 *   data sent during a transfer. Use this option when transmitting sensitive data.
 *   Increases CPU utilization.
 * @property {"encrypt"|"decrypt"} [content_protection] Enable content protection
 *   (encryption-at-rest), which keeps files encrypted on the server. Encrypted
 *   files have the extension ".aspera-env". <br><br>
 *   `encrypt` - Encrypt uploaded files. If `content_protection_passphrase` is
 *   not specified, Connect will prompt for the passphrase. <br><br>
 *   `decrypt` - Decrypt downloaded fiels. If `content_protection_passphrase` is
 *   not specified, Connect will prompt for the passphrase.
 * @property {String} [content_protection_passphrase] A passphrase to encrypt or
 *   decrypt files when using `content_protection`.
 * @property {String} [cookie] Data to associate with the transfer. The cookie is
 *   reported to both client and server-side applications monitoring fasp™ transfers.
 *   It is often used by applications to identify associated transfers.
 * @property {Boolean} [create_dir=false] Creates the destination directory if it
 *   does not already exist. When enabling this option, the destination path is
 *   assumed to be a directory path.
 * @property {Boolean} [obfuscate_file_names=false] If this value is `true`, Connect
 *   will obfuscate all filenames. All files will be renamed to have random names.
 *   Applies only to uploads. This is not reversible.
 * @property {String} [destination_root="/"] The transfer destination file path.
 *   If destinations are specified in `paths`, this value is prepended to each destination.
 *
 *   Note that the download destination paths are relative to the user's Connect
 *   download directory setting unless `ConnectSpec.use_absolute_destination_path`
 *   is enabled.
 * @property {Number} [dgram_size] The IP datagram size for fasp™ to use. If not
 *   specified, fasp™ will automatically detect and use the path MTU as the
 *   datagram size. Use this option only to satisfy networks with strict MTU
 *   requirements.
 * @property {"send"|"receive"} direction Whether to perform an upload or download.
 *
 *   `send` - Upload <br>
 *   `receive` - Download
 * @property {Number} [fasp_port=33001] The UDP port for fasp™ to use. The default value
 *   is satisfactory for most situations. However, it can be changed to satisfy
 *   firewall requirements.
 * @property {Boolean} [http_fallback=false] Attempts to perform an HTTP transfer
 *   if a fasp™ transfer cannot be performed.
 * @property {Number} [http_fallback_port] The port where the Aspera HTTP server is
 *   servicing HTTP transfers. Defaults to port 443 if a `cipher` is enabled, or
 *   port 80 otherwise.
 * @property {Boolean} [lock_min_rate=false] Prevents the user from changing the
 *   minimum rate during a transfer.
 * @property {Boolean} [lock_rate_policy=false] Prevents the user from changing the
 *   rate policy during a transfer.
 * @property {Boolean} [lock_target_rate=false] Prevents the user from changing the
 *   target rate during a transfer.
 * @property {Number} [min_rate_kbps] The minimum speed of the transfer. fasp™
 *   will only share bandwidth exceeding this value.
 *
 *   Note: This value has no effect if `rate_policy` is `fixed`.
 *
 *   Default: Server-side minimum rate default setting (aspera.conf). Will respect
 *   both local and server-side minimum rate caps if set.
 * @property {"always"|"none"|"diff"|"older"|"diff+older"} [overwrite_policy="diff"] Overwrite
 *   destination files with the source files of the same name.
 *
 *   `none` - Never overwrite the file. However, if the parent folder is not empty,
 *   its access, modify, and change times may still be updated.
 *
 *   `always` - Always overwrite the file. The destination file will be overwritten
 *   even if it is identical to the source.
 *
 *   `diff` - Overwrite the file if it is different from the source, depending on
 *   the `resume` property.
 *
 *   `older` - Overwrite the file if its timestamp is older than the source timestamp.
 *
 *   `diff+older` - Overwrite the file if it is older and different than the source,
 *   depending on the `resume` property.
 *
 *   If the `overwrite_policy` is `diff` or `diff+older`, difference is determined by
 *   the `resume` property. If `resume` is empty or `none` is specified, the source
 *   and destination files are always considered different and the destination file
 *   is always overwritten. If `attributes`, the source and destination files are
 *   compared based on file attributes (currently file size). If `sparse_checksum`,
 *   the source and destination files are compared based on sparse checksum. If `full_checksum`,
 *   the source and destination files are compared based on full checksum.
 * @property {Array} paths A list of the file and directory paths to transfer.
 *   Use `destination_root` to specify the destination directory.
 *
 *   *Source list format*
 *   ```
 *     [
 *       {
 *         "source": "/foo"
 *       }, {
 *         "source": "/bar/baz"
 *       },
 *       ...
 *     ]
 *   ```
 *   Optionally specify a destination path - including the file name - for each file.
 *   This format is useful for renaming files or sending to different destinations.
 *   Note that for this format all paths must be file paths (not directory paths).
 *
 *   *Source-Destination pair format*
 *   ```
 *     [
 *       {
 *         "source": "/foo",
 *         "destination": "/qux/foofoo"
 *       }, {
 *         "source": "/bar/baz",
 *         "destination": "/qux/bazbaz"
 *       },
 *       ...
 *     ]
 *   ```
 * @property {"fixed"|"high"|"fair"|"low"} [rate_policy="fair"] The congestion
 *   control behavior to use when sharing bandwidth.
 *
 *   `fixed` - Transfer at the target rate regardless of actual network capacity.
 *   Do not share bandwidth.
 *
 *   `high` - When sharing bandwidth, transfer at twice the rate of a transfer using
 *   "fair" policy.
 *
 *   `fair` - Share bandwidth equally with other traffic.
 *
 *   `low` - Use only unutilized bandwidth.
 * @property {String} remote_host The fully qualified domain name or IP address
 *   of the transfer server.
 * @property {String} [remote_password] The password to use when `authentication`
 *   is set to `password`. If this value is not specified, Connect will prompt
 *   the user.
 * @property {String} [remote_user] The username to use for authentication. For
 *   password authentication, if this value is not specified, Connect will prompt
 *   the user.
 * @property {"none"|"attributes"|"sparse_checksum"|"full_checksum"} [resume="sparse_checksum"]
 *   The policy to use when resuming partially transferred (incomplete) files.
 *
 *   `none` - Transfer the entire file again.
 *
 *   `attributes` - Resume if the files' attributes match.
 *
 *   `sparse_checksum` - Resume if the files' attributes and sparse (fast) checksums
 *   match.
 *
 *   `full_checksum` - Resume if the files' attributes and full checksums match.
 *   Note that computing full checksums of large files takes time, and heavily
 *   utilizes the CPU.
 * @property {String} [source_root="/"] A path to prepend to the source paths specified
 *   in `paths`. If this is not specified, then `paths` should contain absolute
 *   paths.
 * @property {Number} [ssh_port=33001] The server's TCP port that is listening
 *   for SSH connections. fasp™ initiates transfers through SSH.
 * @property {Number} [target_rate_cap_kbps] Limit the transfer rate that the
 *   user can adjust the target and minimum rates to. Default: no limit.
 * @property {Number} [target_rate_kbps] The desired speed of the transfer. If
 *   there is competing network traffic, fasp™ may share this bandwidth, depending
 *   on the `rate_policy`.
 *
 *   Default: Server-side target rate default setting (aspera.conf). Will respect
 *   both local and server-side target rate caps if set.
 * @property {String} [token] Used for token-based authorization, which involves
 *   the server-side application generating a token that gives the client rights
 *   to transfer a predetermined set of files.
 *
 * @example
 * ##### Minimal example
 * {
 *   "paths": [
 *     {
 *       "source": "/foo/1"
 *     }
 *   ],
 *   "remote_host": "10.0.203.80",
 *   "remote_user": "aspera",
 *   "direction": "send"
 * }
 *
 * ##### Download example
 * {
 *   "paths": [
 *     {
 *       "source": "tinyfile0001"
 *     }, {
 *       "source": "tinyfile0002"
 *     }
 *   ],
 *   "obfuscate_file_names": false,
 *   "overwrite_policy": "diff",
 *   "remote_host": "demo.asperasoft.com",
 *   "remote_user": "asperaweb",
 *   "authentication": "password",
 *   "remote_password": "**********",
 *   "fasp_port": 33001,
 *   "ssh_port": 33001,
 *   "http_fallback": true,
 *   "http_fallback_port": 443,
 *   "direction": "receive",
 *   "create_dir": false,
 *   "source_root": "aspera-test-dir-tiny",
 *   "destination_root": "/temp",
 *   "rate_policy": "high",
 *   "target_rate_kbps": 1000,
 *   "min_rate_kbps": 100,
 *   "lock_rate_policy": false,
 *   "target_rate_cap_kbps": 2000,
 *   "lock_target_rate": false,
 *   "lock_min_rate": false,
 *   "resume": "sparse_checksum",
 *   "cipher": "aes-128",
 *   "cookie": "foobarbazqux",
 *   "dgram_size": 1492,
 *   "preserve_times": true,
 *   "tags": {
 *     "your_company": {
 *       "key": "value"
 *     }
 *   }
 * }
 */
/**
 * The data format for the connect web app parameters.
 *
 * @typedef {Object} AsperaConnectSettings
 * @property {String} app_id A secure, random identifier for all transfers
 *   associated with this webapp. Do not hardcode this id. Do not use the same
 *   id for different users. Do not including the host name, product name in the id.
 *   Do not use monotonically increasing ids. If you do not provide one, a
 *   random id will be generated for you and persisted in localStorage.
 * @property {String} back_link Link to the webapp.
 * @property {String} request_id Universally Unique IDentifier for the webapp.
 *
 * @example
 * {
 *   "app_id": "TUyMGQyNDYtM2M1NS00YWRkLTg0MTMtOWQ2OTkxMjk5NGM4",
 *   "back_link": "http://demo.asperasoft.com",
 *   "request_id": "36d3c2a4-1856-47cf-9865-f8e3a8b47822"
 * }
 */
/**
 * This object is returned if an error occurs. It contains an error code and a message.
 *
 * *Note that this is not related to the Javascript `Error` object, but is used
 * only to document the format of errors returned by this API.*
 *
 * @typedef {Object} Error
 *
 * @example
 * {
 *   "error": {
 *     "code": Number,
 *     "internal_message": String,
 *     "user_message": String
 *   }
 * }
 */
/**
 * This object can be passed to an asynchronous API call to get the results
 *   of the call.
 *
 * #### Format
 * ```
 * {
 *   success: function(Object) { ... },
 *   error: function(Error) { ... }
 * }
 * ```
 * The argument passed to the `success` function depends on the original method
 * invoked. The argument to the `error` function is an {@link Error} object.
 *
 * If an Error is thrown during a callback, it is logged to window.console
 * (if supported by the browser).
 *
 * @typedef {Object} types.Callbacks
 */
/**
 * This object holds the data of the files that have been selected by the user. It
 *   may hold one or more data items.
 *
 * #### Format
 * ```
 * {
 *   "dataTransfer" : {
 *     "files": [
 *       {
 *         "lastModifiedDate": "Wed Jan 24 12:22:02 2019",
 *         "name": "/Users/aspera/Desktop/foo.txt",
 *         "size": 386,
 *         "type": "text/plain"
 *       },
 *       {
 *         "lastModifiedDate": "Mon Jan 22 18:01:02 2019",
 *         "name": "/Users/aspera/Desktop/foo.rb",
 *         "size": 609,
 *         "type": "text/x-ruby-script"
 *       }
 *     ]
 *   }
 * }
 * ```
 *
 * @typedef {Object} dataTransfer
 */
/**
 * A set of file extension filters.
 *
 * #### Example
 * ```
 * [
 *   {
 *     filter_name : "Text file",
 *     extensions : ["txt"]
 *   },
 *   {
 *     filter_name : "Image file",
 *     extensions : ["jpg", "png"]
 *   },
 *   {
 *     filter_name : "All types",
 *     extensions : ["*"]
 *   }
 * ]
 * ```
 *
 * @typedef {Object} FileFilters
 */
/**
 * Connect-specific parameters when starting a transfer.
 *
 * @typedef {Object} ConnectSpec
 * @property {Boolean} [allow_dialogs=true] If this value is `false`, Connect will no longer prompt or display windows
 *   automatically, except to ask the user to authorize transfers if the server
 *   is not on the list of trusted hosts.
 * @property {String} [back_link=URL of current page] A URL to associate with the transfer. Connect will display this link
 *   in the context menu of the transfer.
 * @property {Boolean} [return_files=true] If this value is `false`, {@link TransferInfo} will not contain
 *   `files`. Use this option to prevent performance deterioration
 *   when transferring large number of files.
 * @property {Boolean} [return_paths=true] If this value is `false`, the `transfer_spec` property in {@link TransferInfo} will not contain
 *   `paths`. Use this option to prevent performance deterioration
 *   when specifying a large number of source paths.
 * @property {Boolean} [use_absolute_destination_path=false] By default, the destination of a download is relative to the user's Connect
 *   download directory setting. Setting this value to `true` overrides this
 *   behavior, using absolute paths instead.
 *
 * @example
 * {
 *   "allow_dialogs" : false,
 *   "back_link" : "www.foo.com",
 *   "return_paths" : false,
 *   "return_files" : false,
 *   "use_absolute_destination_path" : true
 * }
 */


/***/ }),

/***/ "./src/constants/endpoints.ts":
/*!************************************!*\
  !*** ./src/constants/endpoints.ts ***!
  \************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
exports.apiEndpoints = {
    activity: {
        route: 'activity',
        prefix: '/connect/transfers/'
    },
    authenticate: {
        route: 'authenticate',
        prefix: '/connect/info/'
    },
    droppedFiles: {
        route: 'dropped-files',
        prefix: '/connect/file/'
    },
    getTransfer: {
        route: 'info/${id}',
        prefix: '/connect/transfers/'
    },
    initDragDrop: {
        route: 'initialize-drag-drop',
        prefix: '/connect/file/'
    },
    modifyTransfer: {
        route: 'modify/${id}',
        prefix: '/connect/transfers/'
    },
    ping: {
        route: 'ping',
        prefix: '/connect/info/'
    },
    readAsArrayBuffer: {
        route: 'read-as-array-buffer/',
        prefix: '/connect/file/'
    },
    readChunkAsArrayBuffer: {
        route: 'read-chunk-as-array-buffer/',
        prefix: '/connect/file/'
    },
    getChecksum: {
        route: 'checksum/',
        prefix: '/connect/file/'
    },
    removeTransfer: {
        route: 'remove/${id}',
        prefix: '/connect/transfers/'
    },
    resumeTransfer: {
        route: 'resume/${id}',
        prefix: '/connect/transfers/'
    },
    showAbout: {
        route: 'about',
        prefix: '/connect/windows/'
    },
    showDirectory: {
        route: 'finder/${id}',
        prefix: '/connect/windows/'
    },
    showPreferences: {
        route: 'preferences',
        prefix: '/connect/windows/'
    },
    showPreferencesPage: {
        route: 'preferences/${id}',
        prefix: '/connect/windows/'
    },
    showSaveFileDialog: {
        route: 'select-save-file-dialog/',
        prefix: '/connect/windows/'
    },
    showSelectFileDialog: {
        route: 'select-open-file-dialog/',
        prefix: '/connect/windows/'
    },
    showSelectFolderDialog: {
        route: 'select-open-folder-dialog/',
        prefix: '/connect/windows/'
    },
    showTransferManager: {
        route: 'transfer-manager',
        prefix: '/connect/windows/'
    },
    showTransferMonitor: {
        route: 'transfer-monitor/${id}',
        prefix: '/connect/windows/'
    },
    startTransfer: {
        route: 'start',
        prefix: '/connect/transfers/'
    },
    stopTransfer: {
        route: 'stop/${id}',
        prefix: '/connect/transfers/'
    },
    testSshPorts: {
        route: 'ports',
        prefix: '/connect/info/'
    },
    version: {
        route: 'version',
        prefix: '/connect/info/'
    }
};


/***/ }),

/***/ "./src/constants/http.ts":
/*!*******************************!*\
  !*** ./src/constants/http.ts ***!
  \*******************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
exports.DEFAULT_PORT = 33003;
exports.LOCALHOST = 'http://127.0.0.1:';
exports.MAX_PORT_SEARCH = 20;
exports.MAX_POLLING_ERRORS = 3;
exports.VERSION_PREFIX = '/v6';
exports.SESSION_LASTKNOWN_ID = 'aspera-last-known-session-id';
exports.SESSION_LASTKNOWN_KEY = 'aspera-last-known-session-key';
exports.SESSION_LASTKNOWN_PORT = 'aspera-last-known-port';


/***/ }),

/***/ "./src/constants/index.ts":
/*!********************************!*\
  !*** ./src/constants/index.ts ***!
  \********************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
var tslib_1 = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
tslib_1.__exportStar(__webpack_require__(/*! ./http */ "./src/constants/http.ts"), exports);
tslib_1.__exportStar(__webpack_require__(/*! ./installer */ "./src/constants/installer.ts"), exports);
tslib_1.__exportStar(__webpack_require__(/*! ./local-storage */ "./src/constants/local-storage.ts"), exports);
tslib_1.__exportStar(__webpack_require__(/*! ./shared */ "./src/constants/shared.ts"), exports);
tslib_1.__exportStar(__webpack_require__(/*! ./endpoints */ "./src/constants/endpoints.ts"), exports);


/***/ }),

/***/ "./src/constants/installer.ts":
/*!************************************!*\
  !*** ./src/constants/installer.ts ***!
  \************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
exports.INSTALL_EVENT = {
    DOWNLOAD_CONNECT: 'downloadconnect',
    REFRESH_PAGE: 'refresh',
    IFRAME_REMOVED: 'removeiframe',
    IFRAME_LOADED: 'iframeloaded',
    TROUBLESHOOT: 'troubleshoot',
    CONTINUE: 'continue',
    RESIZE: 'px',
    RETRY: 'retry',
    EXTENSION_INSTALL: 'extension_install',
    DOWNLOAD_EXTENSION: 'download_extension'
};
exports.ACTIVITY_EVENT = {
    CONNECT_BAR_VISIBLE: 'connect_bar_visible',
    CLICKED_INSTALL_EXTENSION: 'clicked_install_extension',
    CLICKED_ENABLE_EXTENSION: 'clicked_enable_extension',
    CLICKED_INSTALL_ADDON: 'clicked_install_addon',
    CLICKED_DOWNLOAD_APP: 'clicked_download_app',
    CLICKED_INSTALL_APP: 'clicked_install_app',
    CLICKED_TROUBLESHOOT: 'clicked_troubleshoot',
    CLICKED_DOWNLOAD_INDICATOR: 'clicked_download_indicator',
    DOWNLOAD_INDICATOR_VISIBLE: 'download_indicator_visible',
    CLICKED_HOW_LINK: 'clicked_how_link',
    CONNECT_BAR_REMOVED: 'connect_bar_removed',
    CLICKED_RETRY: 'mitigate_with_tab'
};
exports.EVENT_TYPE = {
    CONNECT_BAR_EVENT: 'connect_bar_event'
};


/***/ }),

/***/ "./src/constants/local-storage.ts":
/*!****************************************!*\
  !*** ./src/constants/local-storage.ts ***!
  \****************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
exports.LS_CONTINUED_KEY = 'connect-version-continued';
exports.LS_CONNECT_APP_ID = 'connect-app-id';
exports.LS_LOG_KEY = 'aspera-log-level';
exports.LS_CONNECT_DETECTED = 'aspera-last-detected';


/***/ }),

/***/ "./src/constants/shared.ts":
/*!*********************************!*\
  !*** ./src/constants/shared.ts ***!
  \*********************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
exports.MIN_SECURE_VERSION = '3.8.0';
exports.HTTP_METHOD = {
    GET: 'GET',
    POST: 'POST',
    DELETE: 'DELETE',
    REVERT: 'REVERT'
};
exports.STATUS = {
    INITIALIZING: 'INITIALIZING',
    RETRYING: 'RETRYING',
    RUNNING: 'RUNNING',
    OUTDATED: 'OUTDATED',
    FAILED: 'FAILED',
    EXTENSION_INSTALL: 'EXTENSION_INSTALL',
    STOPPED: 'STOPPED',
    WAITING: 'WAITING',
    DEGRADED: 'DEGRADED'
};
exports.EVENT = {
    ALL: 'all',
    TRANSFER: 'transfer',
    STATUS: 'status'
};
exports.TRANSFER_STATUS = {
    CANCELLED: 'cancelled',
    COMPLETED: 'completed',
    FAILED: 'failed',
    INITIATING: 'initiating',
    QUEUED: 'queued',
    REMOVED: 'removed',
    RUNNING: 'running',
    WILLRETRY: 'willretry'
};


/***/ }),

/***/ "./src/core/api.ts":
/*!*************************!*\
  !*** ./src/core/api.ts ***!
  \*************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
var constants_1 = __webpack_require__(/*! ../constants */ "./src/constants/index.ts");
var ApiService = /** @class */ (function () {
    function ApiService(requestHandler) {
        var _this = this;
        this.requestHandler = requestHandler;
        this.send = function (request) {
            var fullEndpoint = _this.getEndpointUrl(request.name, request.param);
            return _this.httpRequest(request.method, fullEndpoint, request.method === 'POST' ? JSON.stringify(request.body) : undefined);
        };
        this.httpRequest = function (method, path, data) {
            var endpoint = {
                method: method,
                path: path,
                body: data
            };
            return _this.requestHandler.start(endpoint);
        };
    }
    /**
     * Forms the URL to use for the API call
     */
    ApiService.prototype.getEndpointUrl = function (name, param) {
        var endpointInfo = constants_1.apiEndpoints[name];
        if (!endpointInfo) {
            throw new Error("Connect API (" + name + ") not known");
        }
        var route = endpointInfo.route;
        var prefix = endpointInfo.prefix;
        if (param) {
            route = route.replace('${id}', param);
        }
        return "" + prefix + route;
    };
    return ApiService;
}());
exports.default = ApiService;


/***/ }),

/***/ "./src/core/request.ts":
/*!*****************************!*\
  !*** ./src/core/request.ts ***!
  \*****************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
var tslib_1 = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
var utils_1 = __webpack_require__(/*! ../utils */ "./src/utils.ts");
var validators_1 = __webpack_require__(/*! ./validators */ "./src/core/validators.ts");
var Request = /** @class */ (function () {
    function Request() {
        this.validators = [];
    }
    Request.prototype.addSettings = function (addStandardSettings) {
        var data = utils_1.copyObject(this.body);
        data = addStandardSettings(data);
        /** Add additional settings for transfer requests */
        if (this.name === 'startTransfer' && this.requestId) {
            var transferSpec = void 0;
            for (var i = 0; i < data.transfer_specs.length; i++) {
                transferSpec = data.transfer_specs[i];
                transferSpec = addStandardSettings(transferSpec);
                transferSpec.aspera_connect_settings.request_id = this.requestId;
                if (utils_1.isNullOrUndefinedOrEmpty(transferSpec.aspera_connect_settings.back_link)) {
                    transferSpec.aspera_connect_settings.back_link = window.location.href;
                }
            }
        }
        this.body = data;
    };
    Request.prototype.setBody = function (body) {
        this.body = body;
        return this;
    };
    Request.prototype.setMethod = function (method) {
        this.method = method;
        return this;
    };
    Request.prototype.setName = function (name) {
        this.name = name;
        return this;
    };
    Request.prototype.setParam = function (param) {
        this.param = param;
        return this;
    };
    Request.prototype.setRequestId = function (id) {
        this.requestId = id;
        return this;
    };
    Request.prototype.setValidator = function () {
        var validators = [];
        for (var _i = 0; _i < arguments.length; _i++) {
            validators[_i] = arguments[_i];
        }
        this.validators = tslib_1.__spread(this.validators, validators);
        return this;
    };
    Request.prototype.validate = function () {
        var _this = this;
        /** Add default validators here */
        this.validators.push(validators_1.validateName, validators_1.validateMethod);
        this.validators.forEach(function (validator) {
            validator(_this);
        });
    };
    Request.prototype.send = function (api) {
        /** Run all validators */
        this.validate();
        return api.send(this);
    };
    return Request;
}());
exports.default = Request;


/***/ }),

/***/ "./src/core/validators.ts":
/*!********************************!*\
  !*** ./src/core/validators.ts ***!
  \********************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
var utils_1 = __webpack_require__(/*! ../utils */ "./src/utils.ts");
exports.validateAuthSpec = function (request) {
    validateOptions(request);
    var required = ['remote_host'];
    validateKeys(request, 'authSpec', required);
};
exports.validateBufferOptions = function (request) {
    validateOptions(request);
    var required = ['path', 'offset', 'chunkSize'];
    validateKeys(request, '#readChunkAsArrayBuffer options', required);
};
exports.validateChecksumOptions = function (request) {
    validateOptions(request);
    var required = ['path'];
    validateKeys(request, '#getChecksum options', required);
    var allowedMethods = ['md5', 'sha1', 'sha256', 'sha512'];
    var body = request.body;
    if (body && body.checksumMethod && allowedMethods.indexOf(body.checksumMethod) === -1) {
        throwError(body.checksumMethod + " is not a supported checksum method");
    }
};
exports.validateMethod = function (request) {
    var method = request.method;
    if (!method) {
        throw new Error('Request is missing property: method');
    }
};
exports.validateName = function (request) {
    var name = request.name;
    if (!name) {
        throw new Error('Request is missing property: name');
    }
};
exports.validateArrayBufferOptions = function (request) {
    validateOptions(request);
    var required = ['path'];
    validateKeys(request, '#readAsArrayBuffer options', required);
};
exports.validateTransferId = function (request) {
    var param = request.param;
    if (utils_1.isNullOrUndefinedOrEmpty(param)) {
        throwError('Must provide transfer id.');
    }
};
var validateBody = function (request, msg) {
    var body = request.body;
    if (utils_1.isNullOrUndefinedOrEmpty(body)) {
        throwError(msg);
    }
};
/**
 * Validate request body contains given keys
 */
var validateKeys = function (request, parameterName, keys) {
    keys.forEach(function (key) {
        if (utils_1.isNullOrUndefinedOrEmpty(request.body[key])) {
            var msg = "Invalid " + parameterName + " parameter: " + key + " is missing or invalid";
            throwError(msg);
        }
    });
};
var validateOptions = function (request) {
    var msg = 'Must provide options parameter.';
    validateBody(request, msg);
};
var throwError = function (msg) {
    var error = new Error(msg);
    error.name = 'ValidationError';
    throw error;
};


/***/ }),

/***/ "./src/helpers/browser.ts":
/*!********************************!*\
  !*** ./src/helpers/browser.ts ***!
  \********************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
var ua = typeof navigator !== 'undefined' ? navigator.userAgent : '';
var checkSafari = function (ua, minver) {
    var match = ua.match(/(?:Version)[\/](\d+(\.\d+)?)/i);
    var ver = parseInt((match && match.length > 1 && match[1] || '0'), 10);
    return (ver >= minver);
};
var checkEdge = function (ua, minver) {
    var match = ua.match(/(?:Edge)[\/](\d+(\.\d+)?)/i);
    var ver = parseInt((match && match.length > 1 && match[1] || '0'), 10);
    return (ver >= minver);
};
var checkFirefox = function (ua, minver) {
    var match = ua.match(/(?:Firefox)[\/](\d+(\.\d+)?)/i);
    var ver = parseInt((match && match.length > 1 && match[1] || '0'), 10);
    return (ver >= minver);
};
exports.default = {
    OPERA: /opera|opr/i.test(ua) && !/edge/i.test(ua),
    IE: /msie|trident/i.test(ua) && !/edge/i.test(ua),
    CHROME: /chrome|crios|crmo/i.test(ua) && !/opera|opr/i.test(ua) && !/edge/i.test(ua),
    FIREFOX: /firefox|iceweasel/i.test(ua) && !/edge/i.test(ua) && checkFirefox(ua, 50),
    FIREFOX_LEGACY: /firefox|iceweasel/i.test(ua) && !/edge/i.test(ua) && !checkFirefox(ua, 50),
    EDGE_CHROMIUM: /edg/i.test(ua) && !/edge/i.test(ua),
    EDGE_WITH_EXTENSION: /edge/i.test(ua) && checkEdge(ua, 14),
    EDGE_LEGACY: /edge/i.test(ua) && !checkEdge(ua, 14),
    SAFARI: /safari/i.test(ua) && !/chrome|crios|crmo/i.test(ua) && !/edge/i.test(ua),
    SAFARI_NO_NPAPI: /safari/i.test(ua) && !/chrome|crios|crmo/i.test(ua) && !/edge/i.test(ua) && checkSafari(ua, 10)
};


/***/ }),

/***/ "./src/helpers/globals.ts":
/*!********************************!*\
  !*** ./src/helpers/globals.ts ***!
  \********************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
/**
 * Expose various shared variables across all modules
 */
var ConnectGlobals = {
    connectVersion: '',
    minVersion: '',
    sessionId: '',
    sessionKey: ''
};
exports.ConnectGlobals = ConnectGlobals;


/***/ }),

/***/ "./src/index.ts":
/*!**********************!*\
  !*** ./src/index.ts ***!
  \**********************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
__webpack_require__(/*! core-js/features/object/assign */ "./node_modules/core-js/features/object/assign.js");
__webpack_require__(/*! core-js/features/array/find */ "./node_modules/core-js/features/array/find.js");
__webpack_require__(/*! core-js/features/promise */ "./node_modules/core-js/features/promise/index.js");
var utils_1 = __webpack_require__(/*! ./utils */ "./src/utils.ts");
var connect_1 = __webpack_require__(/*! ./connect */ "./src/connect.ts");
exports.Connect = connect_1.default;
var installer_1 = __webpack_require__(/*! ./installer */ "./src/installer.ts");
exports.ConnectInstaller = installer_1.ConnectInstaller;
var version_1 = __webpack_require__(/*! ./version */ "./src/version.ts");
exports.__VERSION__ = version_1.__VERSION__;
var logger_1 = __webpack_require__(/*! ./logger */ "./src/logger.ts");
exports.Utils = {
    atou: utils_1.atou,
    getFullURI: utils_1.getFullURI,
    launchConnect: utils_1.launchConnect,
    utoa: utils_1.utoa,
    BROWSER: utils_1.BROWSER
};
exports.Logger = {
    debug: logger_1.debug,
    error: logger_1.error,
    log: logger_1.log,
    setLevel: logger_1.setLevel,
    trace: logger_1.trace,
    warn: logger_1.warn
};
// Necessary in order to support Connect Server AW4 integration.
// For this to work, webpack must use the 'window' libraryTarget.
var LocalizeDirlist = {};
exports.LocalizeDirlist = LocalizeDirlist;
var _window = window;
if (_window.AW4 && _window.AW4.LocalizeDirlist) {
    exports.LocalizeDirlist = LocalizeDirlist = _window.AW4.LocalizeDirlist;
}


/***/ }),

/***/ "./src/installer.ts":
/*!**************************!*\
  !*** ./src/installer.ts ***!
  \**************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
var tslib_1 = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
var Utils = tslib_1.__importStar(__webpack_require__(/*! ./utils */ "./src/utils.ts"));
var Logger = tslib_1.__importStar(__webpack_require__(/*! ./logger */ "./src/logger.ts"));
var extension_1 = __webpack_require__(/*! ./request/strategy/extension */ "./src/request/strategy/extension/index.ts");
var constants_1 = __webpack_require__(/*! ./constants */ "./src/constants/index.ts");
var version_1 = __webpack_require__(/*! ./version */ "./src/version.ts");
/**
 * @classdesc Contains methods to support Connect installation
 *
 * @name ConnectInstaller
 * @class
 * @memberof AW4
 *
 * @property {Object} EVENT Event types
 *
 *   Types:
 *   * `EVENT.DOWNLOAD_CONNECT` - "downloadconnect"
 *   * `EVENT.REFERESH_PAGE` - "refresh"
 *   * `EVENT.IFRAME_REMOVED` - "removeiframe"
 *   * `EVENT.IFRAME_LOADED` - "iframeloaded"
 *   * `EVENT.TROUBLESHOOT` - "troubleshoot"
 *   * `EVENT.CONTINUE` - "continue"
 * @property {Boolean} supportsInstallingExtensions=false To enable Connect extensions,
 *   this property must be set to `true`.
 *
 *   If you have a custom Connect install experience that can handle the EXTENSION_INSTALL state, set this value to 'true'
 *   This value is used by {@link AW4.Connect} to determine if the EXTENSION_INSTALL event should be used.
 *
 * @param {String} [iframeClass="aspera-iframe-container"] Class to be added to
 *   the iframe that is going to be inserted in the DOM, for easier use with a custom stylesheet.
 * @param {String} [iframeId="aspera-iframe-container"] Id of the iframe that is
 *   going to be inserted in the DOM.
 * @param {String} [sdkLocation="//d3gcli72yxqn2z.cloudfront.net/connect/v4"] URL
 *   to the SDK location to serve Connect installers from. Needs to be served in
 *   the same level of security as the web page (HTTP/HTTPS). This option is often used
 *   if you are hosting your own instance of the Connect SDK.
 *
 *   Format:
 *   `//domain/path/to/connect/sdk`
 * @param {"carbon"|"none"} [style="carbon"] Style of the Connect bar design. Specify "none" if you have
 *   a custom Connect install experience.
 * @param {String} [stylesheetLocation] URL to a stylesheet. Needs to be served
 *   in the same level of security as the web page (HTTP/HTTPS).
 *
 *   Format:
 *   `//domain/path/to/css/file.css`
 * @param {Boolean} [oneClick=true] Default installer type to offer users when
 *   visiting the web page.
 * @param {Boolean} [useFips=false] Serve FIPS-compatible Connect installers on Windows.
 *
 * @example
 * let options = {
 *   style: 'carbon'
 * }
 * let asperaInstaller = new AW4.ConnectInstaller(options)
 */
var ConnectInstaller = function ConnectInstaller(options) {
    var _newTarget = this && this instanceof ConnectInstaller ? this.constructor : void 0;
    var _this = this;
    if (!_newTarget) {
        throw new Error('ConnectInstaller() must be called with new');
    }
    ////////////////////////////////////////////////////////////////////////////
    // Private constants
    ////////////////////////////////////////////////////////////////////////////
    var DEFAULT_SDK_LOCATION = '//d3gcli72yxqn2z.cloudfront.net/connect/v4';
    var CONNECT_VERSIONS_JS = '/connectversions.min.js';
    ////////////////////////////////////////////////////////////////////////////
    // Private variables
    ////////////////////////////////////////////////////////////////////////////
    var connectOptions = {};
    var listeners = [];
    var connectJSONreferences;
    var showInstallTimerID = 0;
    var iframeLoadedFlag = false;
    var connectInstallerListeners = [];
    // @ts-ignore :disable:no-unused-variable
    var retryCount = 0;
    if (Utils.isNullOrUndefinedOrEmpty(options)) {
        options = {};
    }
    if (Utils.isNullOrUndefinedOrEmpty(Utils.getLocalStorage('aspera-install-attempted'))) {
        Utils.setLocalStorage('aspera-install-attempted', 'true');
    }
    if (Utils.isNullOrUndefinedOrEmpty(Utils.getLocalStorage('aspera-last-detected'))) {
        Utils.setLocalStorage('aspera-last-detected', '');
    }
    connectOptions.iframeId = options.iframeId || 'aspera-iframe-container';
    connectOptions.oneClick = options.oneClick === false ? false : true;
    connectOptions.useFips = options.useFips === true ? true : false;
    connectOptions.sdkLocation = (Utils.isNullOrUndefinedOrEmpty(options.sdkLocation)) ? DEFAULT_SDK_LOCATION : Utils.getFullURI(options.sdkLocation);
    connectOptions.stylesheetLocation = Utils.getFullURI(options.stylesheetLocation);
    connectOptions.correlationId = options.correlationId;
    // Allow 'none' but default to 'carbon'
    connectOptions.style = options.style === 'none' ? 'none' : 'carbon';
    if (typeof (Storage) !== 'undefined') {
        var overrideStyle = Utils.getLocalStorage('aspera-connect-install-style');
        if (overrideStyle) {
            connectOptions.style = overrideStyle;
        }
    }
    if (connectOptions.style === 'carbon') {
        ConnectInstaller.supportsInstallingExtensions = true;
    }
    ////////////////////////////////////////////////////////////////////////////
    // Helper Functions
    ////////////////////////////////////////////////////////////////////////////
    /*
     * loadFiles(files, type, callback) -> null
     * - files (Array): Set of files to load
     * - type (String): type of the files to load: `js` or `css`
     * - callback (function): to be called when all scripts provided have been loaded,
     *
     *     `true` : if files loaded correctly
     *
     *     `false` : if files failed to load
     *
     */
    var loadFiles = function (files, type, callback) {
        if (files === null || typeof files === 'undefined' || !(files instanceof Array)) {
            return;
        }
        else if (type === null || typeof type !== 'string') {
            return;
        }
        var numberOfFiles = 0;
        var head = document.getElementsByTagName('head')[0] || document.documentElement;
        /* Loads the file given, and sets a callback, when the file is the last one and a callback is
         * provided, it will call it
         * Loading mechanism based on https://jquery.org (MIT license)
         */
        var loadFilesHelper = function (file) {
            // IE9+ supports both script.onload AND script.onreadystatechange thus the done check
            var done = false;
            var fileref;
            if (type.toLowerCase() === 'js') {
                fileref = document.createElement('script');
                fileref.setAttribute('type', 'text/javascript');
                fileref.setAttribute('src', file);
            }
            else if (type.toLowerCase() === 'css') {
                fileref = document.createElement('link');
                fileref.setAttribute('rel', 'stylesheet');
                fileref.setAttribute('type', 'text/css');
                fileref.setAttribute('href', file);
            }
            else {
                return;
            }
            if (typeof callback === 'function') {
                /** Attach handlers for all browsers */
                fileref.onload = fileref.onreadystatechange = function () {
                    if (!done && (!this.readyState || this.readyState === 'loaded' || this.readyState === 'complete')) {
                        done = false;
                        /** Handle memory leak in IE */
                        // tslint:disable-next-line
                        fileref.onload = fileref.onreadystatechange = null;
                        if (head && fileref.parentNode) {
                            head.removeChild(fileref);
                        }
                        if (--numberOfFiles <= 0 && typeof callback === 'function') {
                            callback(true);
                        }
                    }
                };
                fileref.onerror = function () {
                    callback(false);
                };
            }
            // Use insertBefore instead of appendChild  to circumvent an IE6 bug.
            head.insertBefore(fileref, head.firstChild);
        };
        numberOfFiles = files.length;
        for (var i = 0; i < numberOfFiles; i++) {
            if (typeof files[i] === 'string') {
                loadFilesHelper(files[i]);
            }
        }
    };
    var osPlatform = function () {
        var os = 'Not supported';
        if (/Win/.test(navigator.platform)) {
            if (navigator.userAgent.indexOf('WOW64') !== -1 || navigator.userAgent.indexOf('Win64') !== -1) {
                os = 'Win64';
            }
            else {
                os = 'Win32';
            }
        }
        else if (/CrOS/.test(navigator.userAgent)) {
            // Chrome OS not supported
            return os;
        }
        else if (/Mac OS X 10[._]6/.test(navigator.userAgent)) {
            os = 'MacIntel-10.6-legacy';
        }
        else if (/Mac/.test(navigator.platform)) {
            os = 'MacIntel';
        }
        else if (/Linux x86_64/.test(navigator.platform)) {
            os = 'Linux x86_64';
        }
        else if (/Linux/.test(navigator.platform)) {
            os = 'Linux i686';
        }
        return os;
    };
    var osVersion = function () {
        var match = '';
        if (/Win/.test(navigator.platform)) {
            match = navigator.userAgent.match(/Windows NT (\d+)[._](\d+)/);
        }
        else if (/Mac/.test(navigator.platform)) {
            match = navigator.userAgent.match(/OS X (\d+)[._](\d+)/);
        }
        if (Utils.isNullOrUndefinedOrEmpty(match)) {
            return;
        }
        var osVersion = {
            highWord: parseFloat(match[1]),
            loWord: parseFloat(match[2])
        };
        return osVersion;
    };
    var platformVersion = function (arg0) {
        if (!Utils.isNullOrUndefinedOrEmpty(arg0)) {
            var match = arg0.match(/(\d+)[.](\d+)/);
            if (Utils.isNullOrUndefinedOrEmpty(match)) {
                return;
            }
            var platformVersion_1 = {
                highWord: parseFloat(match[1]),
                loWord: parseFloat(match[2])
            };
            return platformVersion_1;
        }
        return arg0;
    };
    var notifyListeners = function (event) {
        for (var i = 0; i < listeners.length; i++) {
            listeners[i](event);
        }
    };
    var notifyActivityListeners = function (status) {
        for (var i = 0; i < connectInstallerListeners.length; i++) {
            connectInstallerListeners[i](status);
        }
    };
    var addStyleString = function (str) {
        var node = document.createElement('style');
        node.setAttribute('type', 'text/css');
        // Fix for <= IE9
        // @ts-ignore
        if (node.styleSheet) {
            // @ts-ignore
            node.styleSheet.cssText = str;
        }
        else {
            node.innerHTML = str;
        }
        document.body.appendChild(node);
    };
    var supportsExtensions = function () {
        return ((Utils.BROWSER.CHROME || Utils.BROWSER.EDGE_WITH_EXTENSION || Utils.BROWSER.FIREFOX) ||
            Utils.BROWSER.SAFARI_NO_NPAPI);
    };
    ////////////////////////////////////////////////////////////////////////////
    // API Functions
    ////////////////////////////////////////////////////////////////////////////
    this.addEventListener = function (listener) {
        if (typeof listener !== 'function') {
            return;
        }
        listeners.push(listener);
        return;
    };
    this.addActivityListener = function (type, listener) {
        if (typeof listener !== 'function') {
            return;
        }
        if (type === constants_1.EVENT_TYPE.CONNECT_BAR_EVENT) {
            connectInstallerListeners.push(listener);
        }
        return;
    };
    /**
     * Queries the Connect SDK for the current system's information, returning the full spec of all the
     * documentation and binaries available for it.
     *
     * @function
     * @name AW4.ConnectInstaller#installationJSON
     * @param {Function} callbacks Function that will be called when the result is
     *   retrieved.
     *
     *   Object returned to callback function:
     *   ```
     *   {
     *     "title": "Aspera Connect for Windows",
     *     "platform": {
     *         "os": "win32"
     *    },
     *     "navigator": {
     *         "platform": "Win32"
     *     },
     *     "version": "3.10.0.105660",
     *     "id": "urn:uuid:589F9EE5-0489-4F73-9982-A612FAC70C4E",
     *     "updated": "2012-10-30T10:16:00+07:00",
     *     "links": [
     *         {
     *             "title": "Windows Installer",
     *             "type": "application/octet-stream",
     *             "href": "bin/AsperaConnect-ML-3.10.0.105660.msi",
     *             "hreflang": "en",
     *             "rel": "enclosure"
     *         },
     *         {
     *             "title": "Aspera Connect Release Notes for Windows",
     *             "type": "text/html",
     *             "href": "https://www.ibm.com/support/knowledgecenter/SSXMX3_3.9.9/relnote/connect_relnotes.html",
     *             "hreflang": "en",
     *             "rel": "release-notes"
     *         }
     *       ]
     *   }
     *   ```
     * @return {null}
     */
    this.installationJSON = function (callback) {
        var _this = this;
        if (typeof callback !== 'function') {
            return;
        }
        if (connectJSONreferences !== undefined) {
            callback(connectJSONreferences);
            return;
        }
        var updatesURL = connectOptions.sdkLocation;
        var replaceJSONWithFullHref = function (connectversionsSdkLocation, entryJSON) {
            for (var i = 0; i < entryJSON.links.length; i++) {
                var hrefLink = entryJSON.links[i].href;
                if (!/^https?:\/\//i.test(hrefLink) && !/^\/\//.test(hrefLink)) {
                    entryJSON.links[i].hrefAbsolute = connectversionsSdkLocation + '/' + hrefLink;
                }
            }
        };
        // load references from file and parse to load in the iframe
        var parseIstallJSON = function (connectversionsSdkLocation) {
            var parsedInstallJSON = window.connectVersions;
            var installEntries = parsedInstallJSON.entries;
            var procesJSONentry = function (entryJSON) {
                replaceJSONWithFullHref(connectversionsSdkLocation, entryJSON);
                connectJSONreferences = entryJSON;
                callback(entryJSON);
            };
            var userOS = osPlatform();
            for (var i = 0; i < installEntries.length; i++) {
                var entry = installEntries[i];
                if (entry.navigator.platform === userOS) {
                    var userOSVersion = osVersion();
                    var currentPlatform = platformVersion(entry.platform.version);
                    if (!Utils.isNullOrUndefinedOrEmpty(currentPlatform) && !Utils.isNullOrUndefinedOrEmpty(userOSVersion)) {
                        if ((userOSVersion.highWord > currentPlatform.highWord) ||
                            (userOSVersion.highWord >= currentPlatform.highWord &&
                                userOSVersion.loWord >= currentPlatform.loWord)) {
                            procesJSONentry(entry);
                            return;
                        }
                    }
                    else {
                        procesJSONentry(entry);
                        return;
                    }
                }
            }
            _this.showUnsupportedBrowser();
        };
        var scriptLoaded = function (success) {
            var fallbackURL = DEFAULT_SDK_LOCATION; // connectOptions.sdkLocation;
            if (success && window.connectVersions !== undefined) {
                parseIstallJSON(updatesURL);
            }
            else if (updatesURL !== fallbackURL) {
                updatesURL = fallbackURL;
            }
        };
        loadFiles([updatesURL + CONNECT_VERSIONS_JS], 'js', scriptLoaded);
        return;
    };
    /**
     * @ignore
     *
     * Determines if user has already installed the Connect extensions.
     *
     * *This method is asynchronous.*
     *
     * @function
     * @name AW4.ConnectInstaller#isExtensionInstalled
     * @param {Number} timeout Timeout (in milliseconds) to wait before the extension
     *   is considered not to be installed.
     * @param {Callbacks} callbacks `success` and `timedout` functions to receive
     *   results.
     * @return {null}
     */
    this.isExtensionInstalled = function (timeout, callbacks) {
        // Prereq: asperaweb-4 needs to be loaded first
        // @ts-ignore
        var extReqImpl = Utils.BROWSER.SAFARI_NO_NPAPI ? new extension_1.SafariAppStrategy() : new extension_1.NativeHostStrategy();
        if (!supportsExtensions()) {
            Logger.debug('This browser does not use extensions.');
            return;
        }
        if (callbacks) {
            extReqImpl.detectExtension(timeout).then(function (success) {
                if (success && typeof callbacks.success === 'function') {
                    callbacks.success();
                }
                if (!success && typeof callbacks.timedout === 'function') {
                    callbacks.timedout();
                }
            }).catch(function (err) {
                Logger.debug('Error trying to detect extension:', err);
                if (typeof callbacks.timedout === 'function') {
                    callbacks.timedout();
                }
            });
        }
        else {
            return extReqImpl.detectExtension(timeout);
        }
    };
    /**
     * Determine if current browser requires web store to install extensions.
     *
     * @function
     * @name AW4.ConnectInstaller#doesBrowserNeedExtensionStore
     * @return {Boolean}
     */
    this.doesBrowserNeedExtensionStore = function () {
        if (Utils.BROWSER.CHROME === true ||
            Utils.BROWSER.FIREFOX === true ||
            Utils.BROWSER.EDGE_WITH_EXTENSION === true) {
            return true;
        }
        // IE = ActiveX
        // Safari = bundled app extension
        return false;
    };
    /**
     * For supported browsers, returns a url for extension installation.
     *
     * @function
     * @name AW4.ConnectInstaller#getExtensionStoreLink
     * @return {String}
     *
     * @example
     * // On a Chrome browser
     * asperaInstaller.getExtensionStoreLink()
     * // returns "https://chrome.google.com/webstore/detail/ibm-aspera-connect/kpoecbkildamnnchnlgoboipnblgikpn"
     */
    this.getExtensionStoreLink = function () {
        if (Utils.BROWSER.FIREFOX === true) {
            return 'https://addons.mozilla.org/en-US/firefox/addon/ibm-aspera-connect';
        }
        else if (Utils.BROWSER.EDGE_WITH_EXTENSION === true) {
            return 'ms-windows-store://pdp/?productid=9N6XL57H8BMG';
        }
        else if (Utils.BROWSER.EDGE_CHROMIUM === true) {
            return 'https://microsoftedge.microsoft.com/addons/detail/ibm-aspera-connect/kbffkbiljjejklcpnfmoiaehplhcifki';
        }
        else if (Utils.BROWSER.CHROME === true) {
            return 'https://chrome.google.com/webstore/detail/ibm-aspera-connect/kpoecbkildamnnchnlgoboipnblgikpn';
        }
        Logger.debug('This browser does not use extensions.');
        return '';
    };
    /**
     * AW4.ConnectInstaller#startExtensionInstall() -> null
     *
     * In supported browsers, starts the extension installation experience.
     * To avoid issues with popup blockers, considering using anchor tag with `AW4.ConnectInstaller#getExtensionStoreLink`
     *
     */
    this.startExtensionInstall = function () {
        var lnk = this.getExtensionStoreLink();
        if (lnk !== '') {
            window.open(lnk, '_blank');
        }
    };
    var getRefreshWindow = function () {
        // Fix for refreshing only window in which we are contained, if we are an iframe just refresh the iframe (Sharepoint bug)
        var inIframe = false;
        try {
            inIframe = window.self !== window.top;
        }
        catch (e) {
            inIframe = true;
        }
        var refreshWindow = window;
        // NOTE: contentWindow used in thsi way will always be undefined according to its HTML specification
        if (inIframe) {
            var iframe = document.getElementById(connectOptions.iframeId);
            if (iframe.contentWindow) {
                refreshWindow = iframe.contentWindow;
            }
        }
        return refreshWindow;
    };
    // Get top window href and open in new tab
    var openNewTab = function () {
        var url = window.top.location.href;
        window.open(url, '_blank');
    };
    var isActivityEvent = function (e) {
        for (var key in constants_1.ACTIVITY_EVENT) {
            if (constants_1.ACTIVITY_EVENT[key] === e) {
                return true;
            }
        }
        return false;
    };
    /*
     * AW4.ConnectInstaller#show(eventType) -> null
     * - eventType (String): the event type
     *
     * ##### Event types
     *
     * 1. `connecting` (`String`).
     * 2. `unable-to-launch` (`String`).
     * 3. `refresh` (`String`).
     * 4. `outdated` (`String`).
     * 5. `running` (`String`).
     *
     */
    var show = function (eventType) {
        // We always need to check if launching was going to be popped up, if so delete it
        if (showInstallTimerID !== 0) {
            clearTimeout(showInstallTimerID);
        }
        var iframe = document.getElementById(connectOptions.iframeId);
        // To support old browser that don't have it
        if (typeof (String.prototype.endsWith) === 'undefined') {
            String.prototype.endsWith = function (suffix) {
                return this.indexOf(suffix, this.length - suffix.length) !== -1;
            };
        }
        // IE will complain that in strict mode functions cannot be nested inside a statement, so we have to define it here
        var handleMessage = function (event) {
            // iFrame installation: Handling of messages by the parent window.
            if (isActivityEvent(event.data)) {
                Logger.debug('Connect bar activity: ', event.data);
                notifyActivityListeners(event.data);
                if (event.data === constants_1.ACTIVITY_EVENT.CLICKED_DOWNLOAD_APP) {
                    // Track if the user downloaded the app
                    Utils.setLocalStorage('aspera-connect-app-download', Date.now().toString());
                }
                else if (event.data === constants_1.ACTIVITY_EVENT.CLICKED_INSTALL_APP) {
                    if (Utils.BROWSER.SAFARI || Utils.BROWSER.IE) {
                        // Transition to extension_installs state if user clicks install app
                        //   on Safari or IE.
                        _this.showExtensionInstall();
                    }
                }
                else if (event.data === constants_1.ACTIVITY_EVENT.CLICKED_RETRY) {
                    openNewTab();
                }
            }
            if (event.data === constants_1.INSTALL_EVENT.DOWNLOAD_CONNECT) {
                notifyListeners(event.data);
                _this.showInstall();
            }
            else if (event.data === constants_1.INSTALL_EVENT.DOWNLOAD_EXTENSION) {
                notifyListeners(event.data);
                _this.showDownload();
            }
            else if (event.data === constants_1.INSTALL_EVENT.REFRESH_PAGE) {
                notifyListeners(event.data);
                var refreshWindow = getRefreshWindow();
                // tslint:disable-next-line
                refreshWindow.location.reload(true);
            }
            else if (event.data === constants_1.INSTALL_EVENT.IFRAME_REMOVED) {
                notifyListeners(event.data);
                _this.dismiss();
            }
            else if (event.data === constants_1.INSTALL_EVENT.TROUBLESHOOT) {
                notifyListeners(event.data);
                var refreshWindow = getRefreshWindow();
                refreshWindow.location.href = 'https://test-connect.asperasoft.com';
            }
            else if (event.data === constants_1.INSTALL_EVENT.CONTINUE) {
                Utils.addVersionException();
                notifyListeners(event.data);
                if ((Utils.BROWSER.SAFARI && !Utils.BROWSER.SAFARI_NO_NPAPI) || Utils.BROWSER.IE) {
                    var refreshWindow = getRefreshWindow();
                    // tslint:disable-next-line
                    refreshWindow.location.reload(true);
                }
                else {
                    _this.showLaunching();
                }
            }
            else if (event.data === constants_1.INSTALL_EVENT.RETRY) {
                notifyListeners(event.data);
                _this.showLaunching();
            }
            else if (event.data === '100%') {
                iframe.setAttribute('style', 'height:100%;width:100%;max-width: 100%;margin: 0 auto;background-color:rgba(223, 227, 230, 0.75);');
            }
            else if (typeof event.data === 'string' && event.data.endsWith(constants_1.INSTALL_EVENT.RESIZE)) {
                iframe.style.height = event.data;
                iframe.style.maxWidth = '600px';
            }
            else if (event.data === constants_1.INSTALL_EVENT.EXTENSION_INSTALL) {
                notifyListeners(event.data);
                _this.startExtensionInstall();
            }
        };
        // IE will complain that in strict mode functions cannot be nested inside a statement, so we have to define it here
        var iframeLoaded = function () {
            iframeLoadedFlag = true;
            notifyListeners(constants_1.INSTALL_EVENT.IFRAME_LOADED);
            var iframe = document.getElementById(connectOptions.iframeId);
            if (Utils.BROWSER.SAFARI || Utils.BROWSER.IE) {
                var downloadTimestamp = Utils.getLocalStorage('aspera-connect-app-download');
                if (!Utils.isNullOrUndefinedOrEmpty(downloadTimestamp)) {
                    iframe.contentWindow.postMessage('downloadTimestamp=' + downloadTimestamp, '*');
                }
            }
            // populate the iframe with the information pulled from connectversions.js
            var populateIframe = function (referencesJSON) {
                if (referencesJSON) {
                    for (var i = 0; i < referencesJSON.links.length; i++) {
                        var link = referencesJSON.links[i];
                        // Defaults to setting one click installer unless ConnectInstaller was
                        //   passed oneClick = false.
                        var rel = connectOptions.oneClick ? 'enclosure-one-click' : 'enclosure';
                        // Serve FIPS installers if opted in
                        if (connectOptions.useFips && /Win/.test(navigator.platform)) {
                            rel = rel + '-fips';
                        }
                        if (link.rel === rel) {
                            if (typeof iframe !== 'undefined' && iframe !== null) {
                                iframe.contentWindow.postMessage('downloadlink=' + link.hrefAbsolute, '*');
                                iframe.contentWindow.postMessage('downloadVersion=' + referencesJSON.version, '*');
                            }
                        }
                    }
                }
                else {
                    Logger.error('Could not load Connect installation json!');
                }
                // Set dialog type
                iframe.contentWindow.postMessage(eventType, '*');
            };
            _this.installationJSON(populateIframe);
            // load an stylesheet if provided
            if (connectOptions.stylesheetLocation) {
                // Inserting a stylesheet into the DOM for more manageable styles.
                if (typeof iframe !== 'undefined' && iframe !== null) {
                    iframe.contentWindow.postMessage('insertstylesheet=' + connectOptions.stylesheetLocation, '*');
                }
            }
            if (connectOptions.correlationId) {
                iframe.contentWindow.postMessage('correlationId=' + connectOptions.correlationId, '*');
            }
            if (version_1.__VERSION__) {
                iframe.contentWindow.postMessage('sdkVersion=' + version_1.__VERSION__, '*');
            }
            notifyListeners(constants_1.INSTALL_EVENT.IFRAME_LOADED);
        };
        if (!iframe) {
            if (connectOptions.style === 'none') {
                Logger.debug('style=none specified, will not load banner.');
                return;
            }
            // Set iframe styling
            if (connectOptions.style === 'carbon') {
                addStyleString('.' + connectOptions.iframeId + '{width: 100%;max-width: 600px;height: 80px;margin: 0 auto;position: fixed;top: 0;right: 0;left: 0;z-index: 9999;box-shadow: 0 12px 24px 0 rgba(0, 0, 0, 0.1)}');
            }
            else if (connectOptions.style === 'blue') {
                addStyleString('.' + connectOptions.iframeId + '{position: absolute;width: 100%;height: 80px;margin: 0px;padding: 0px;border: none;outline: none;overflow: hidden;top: 0px;left: 0px;z-index: 999999999}');
            }
            // Build and insert the iframe.
            iframe = document.createElement('iframe');
            iframe.id = connectOptions.iframeId;
            iframe.className = connectOptions.iframeId;
            // tslint:disable-next-line
            iframe.frameBorder = '0';
            if (connectOptions.style === 'carbon') {
                iframe.src = connectOptions.sdkLocation + '/install/carbon-installer/index.html';
            }
            else if (connectOptions.style === 'blue') {
                iframe.src = connectOptions.sdkLocation + '/install/auto-topbar/index.html';
            }
            document.body.appendChild(iframe);
            // Check for tight security policies
            if (!iframe.contentWindow.postMessage) {
                return;
            }
            // Set listener for messages from the iframe installer.
            if (window.attachEvent) {
                window.attachEvent('onmessage', handleMessage);
            }
            else {
                window.addEventListener('message', handleMessage, false);
            }
        }
        // if the iframe is hidden due to dismiss, reset the display style
        iframe.style.display = '';
        if (iframeLoadedFlag) {
            iframe.contentWindow.postMessage(eventType, '*');
        }
        else {
            // Give time to the iFrame to be loaded #31040
            // @ts-ignore
            if (iframe.attachEvent) {
                // @ts-ignore
                iframe.attachEvent('onload', iframeLoaded);
            }
            else {
                iframe.onload = iframeLoaded;
            }
        }
    };
    // Direct communication from Connect
    window.addEventListener('message', function (event) {
        if (event.data === 'show_extension_install') {
            _this.showExtensionInstall();
        }
        else if (event.data === 'show_safari_mitigate') {
            show('safari_mitigate');
        }
    }, false);
    /**
     * Displays a banner at the top of the screen explaining to the user that Connect
     * is trying to be launched.
     *
     * @function
     * @name AW4.ConnectInstaller#showLaunching
     * @param {Number} [timeout=3500] Timeout to show the banner in milliseconds. If at any point
     *   during this timeout {@link AW4.ConnectInstaller#connected} or {@link AW4.ConnectInstaller#dismiss}
     *   are called, the banner will not appear.
     * @return {null}
     */
    this.showLaunching = function (timeout) {
        if (timeout === void 0) { timeout = 3500; }
        if (showInstallTimerID !== 0) {
            clearTimeout(showInstallTimerID);
        }
        var showLaunchingHelperFunction = function () {
            show('launching');
        };
        showInstallTimerID = setTimeout(showLaunchingHelperFunction, timeout);
    };
    /**
     * Displays a banner at the top of the screen notifying the user to download Connect.
     *
     * @function
     * @name AW4.ConnectInstaller#showDownload
     * @return {null}
     */
    this.showDownload = function () {
        show('download');
    };
    /**
     * Displays a banner at the top of the screen explaining to the user what to do once
     * Connect has been downloaded.
     *
     * @function
     * @name AW4.ConnectInstaller#showInstall
     * @return {null}
     */
    this.showInstall = function () {
        show('install');
        Utils.setLocalStorage('aspera-install-attempted', 'true');
    };
    /**
     * Displays a banner at the top of the screen notifying the user to update Connect
     * to the latest version.
     *
     * @function
     * @name AW4.ConnectInstaller#showUpdate
     * @return {null}
     */
    this.showUpdate = function () {
        show('update');
    };
    /**
     * Displays a banner with the option to retry launching Connect.
     *
     * @function
     * @name AW4.ConnectInstaller#showRetry
     * @return {null}
     */
    this.showRetry = function () {
        show('retry');
        retryCount++;
    };
    /**
     * Displays a page with instructions to install the browser extension.
     *
     * @function
     * @name AW4.ConnectInstaller#showExtensionInstall
     * @return {null}
     */
    this.showExtensionInstall = function () {
        show('extension_install');
        if (!Utils.BROWSER.IE) {
            // Create a DOM element to help the extension return to the right page
            var extHelper = document.createElement('div');
            extHelper.className = 'aspera-connect-ext-locator'; // TODO: Document
            extHelper.style.display = 'none';
            document.body.appendChild(extHelper);
        }
    };
    /**
     * Displays the last page that was shown.
     *
     * @function
     * @name AW4.ConnectInstaller#showPrevious
     * @return {null}
     */
    this.showPrevious = function () {
        show('previous');
    };
    /**
     * Displays a banner explaining that the browser is not supported by Connect.
     *
     * @function
     * @name AW4.ConnectInstaller#showUnsupportedBrowser
     * @return {null}
     */
    this.showUnsupportedBrowser = function () {
        show('unsupported_browser');
    };
    /**
     * Displays a temporary message that Connect has been found, and after `timeout` dismisses the
     * banner
     *
     * @function
     * @name AW4.ConnectInstaller#connected
     *
     * @param {Number} [timeout=2000] Timeout (in milliseconds) until the banner
     *   is dismissed..
     * @return {null}
     */
    this.connected = function (timeout) {
        if (timeout === void 0) { timeout = 2000; }
        clearTimeout(showInstallTimerID);
        var iframe = document.getElementById(connectOptions.iframeId);
        if (typeof iframe !== 'undefined' && iframe !== null) {
            show('running');
            setTimeout(_this.dismiss, timeout);
            Utils.setLocalStorage('aspera-last-detected', String(Date.now().toString()));
        }
        return;
    };
    /**
     * Dismisses the banner.
     *
     * @function
     * @name AW4.ConnectInstaller#dismiss
     * @return {null}
     */
    this.dismiss = function () {
        if (showInstallTimerID !== 0) {
            clearTimeout(showInstallTimerID);
        }
        var iframe = document.getElementById(connectOptions.iframeId);
        if (typeof iframe !== 'undefined' && iframe !== null) {
            iframe.style.display = 'none';
        }
        return;
    };
};
exports.ConnectInstaller = ConnectInstaller;
ConnectInstaller.EVENT = constants_1.INSTALL_EVENT;
ConnectInstaller.ACTIVITY_EVENT = constants_1.ACTIVITY_EVENT;
ConnectInstaller.EVENT_TYPE = constants_1.EVENT_TYPE;
ConnectInstaller.supportsInstallingExtensions = false;


/***/ }),

/***/ "./src/logger.ts":
/*!***********************!*\
  !*** ./src/logger.ts ***!
  \***********************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

/**
 * @desc Contains logging wrapper functions for the developer.
 *
 * @module Logger
 */
Object.defineProperty(exports, "__esModule", { value: true });
var constants_1 = __webpack_require__(/*! ./constants */ "./src/constants/index.ts");
var LEVEL = {
    INFO: 0,
    DEBUG: 1,
    TRACE: 2
};
var LogLevel = LEVEL.INFO;
if (typeof localStorage !== 'undefined' && localStorage.hasOwnProperty(constants_1.LS_LOG_KEY)) {
    LogLevel = Number(localStorage.getItem(constants_1.LS_LOG_KEY));
}
function trace() {
    var args = [];
    for (var _i = 0; _i < arguments.length; _i++) {
        args[_i] = arguments[_i];
    }
    if (LogLevel >= LEVEL.TRACE) {
        print('log', args);
    }
}
exports.trace = trace;
function debug() {
    var args = [];
    for (var _i = 0; _i < arguments.length; _i++) {
        args[_i] = arguments[_i];
    }
    if (LogLevel >= LEVEL.DEBUG) {
        print('log', args);
    }
}
exports.debug = debug;
/**
 * AW4.Logger.log(message) -> No return value
 * -message (String): A check for if window.console is defined is performed,
 * and if window.console is defined, then message will be sent to
 * console.log.
 */
function log() {
    var args = [];
    for (var _i = 0; _i < arguments.length; _i++) {
        args[_i] = arguments[_i];
    }
    print('log', args);
}
exports.log = log;
/**
 * AW4.Logger.warn(message) -> No return value
 * -message (String): A check for if window.console is defined is performed,
 * and if window.console is defined, then message will be sent to
 * console.warn.
 */
function warn() {
    var args = [];
    for (var _i = 0; _i < arguments.length; _i++) {
        args[_i] = arguments[_i];
    }
    print('warn', args);
}
exports.warn = warn;
/**
 * AW4.Logger.error(message) -> No return value
 * -message (String): A check for if window.console is defined is performed,
 * and if window.console is defined, then message will be sent to
 * console.error.
 */
function error() {
    var args = [];
    for (var _i = 0; _i < arguments.length; _i++) {
        args[_i] = arguments[_i];
    }
    print('error', args);
}
exports.error = error;
function print(level, message) {
    if (typeof window.console !== 'undefined') {
        (console)[level].apply(console, message);
    }
}
/**
 * Sets the logging level for the Connect SDK.
 *
 * @function
 * @static
 * @name setLevel
 * @param {Number} level=0
 * Levels:
 * * `0` - INFO
 * * `1` - DEBUG
 * * `2` - TRACE
 * @return {null}
 */
function setLevel(level) {
    LogLevel = level;
    localStorage[constants_1.LS_LOG_KEY] = level;
}
exports.setLevel = setLevel;


/***/ }),

/***/ "./src/request/handler.ts":
/*!********************************!*\
  !*** ./src/request/handler.ts ***!
  \********************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
var tslib_1 = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
var Logger = tslib_1.__importStar(__webpack_require__(/*! ../logger */ "./src/logger.ts"));
var Utils = tslib_1.__importStar(__webpack_require__(/*! ../utils */ "./src/utils.ts"));
var constants_1 = __webpack_require__(/*! ../constants */ "./src/constants/index.ts");
var globals_1 = __webpack_require__(/*! ../helpers/globals */ "./src/helpers/globals.ts");
var provider_1 = tslib_1.__importDefault(__webpack_require__(/*! ./strategy/provider */ "./src/request/strategy/provider.ts"));
/**
 * Stategy pattern for selecting the correct request implementation during runtime
 * given the user's configuration options.
 *
 * TODO: Remove dependency on session id. Specific to http strategy.
 */
var RequestHandler = /** @class */ (function () {
    function RequestHandler(_options) {
        var _this = this;
        this._options = _options;
        /** Debugging variable to keep track of multiple instances */
        this._objectId = 0;
        /** Simple counter to increment request ids */
        this._nextId = 0;
        this.versionChecked = false;
        this.connectStatus = constants_1.STATUS.INITIALIZING;
        /** Internal cache for active requests */
        this._idRequestHash = {};
        /** Array in which we are going to store all the requests that cannot be processed at this time */
        this._queue = [];
        /** Track number of polling errors for debounce */
        this._pollingRequestErrors = 0;
        /** Internal state of the request handler */
        this._handlerStatus = '';
        /**
         * Process all pending client requests starting with most recent
         */
        this.processQueue = function () {
            var _loop_1 = function () {
                var requestInfo = _this._queue.pop();
                if (requestInfo) {
                    var endpoint = {
                        method: requestInfo.method,
                        path: requestInfo.path,
                        body: requestInfo.body
                    };
                    Logger.debug("Processing request queue for endpoint: " + endpoint.path);
                    _this._strategy.httpRequest(endpoint, requestInfo.requestId)
                        .then(function (response) {
                        return _this.handleResponse(response);
                    })
                        .then(function (response) {
                        if (requestInfo && requestInfo.resolve) {
                            requestInfo.resolve(response);
                        }
                    })
                        .catch(function (error) {
                        // Note: queued activity requests will return 404 by Connect since app_id is empty
                        if (requestInfo && requestInfo.reject) {
                            requestInfo.reject(error);
                        }
                    });
                }
            };
            while (_this._queue.length > 0) {
                _loop_1();
            }
            Logger.debug('Request queue empty.');
        };
        this.changeConnectStatus = function (newConnectStatus) {
            /**
             * Make sure we check the Connect version before going to running. Happens
             * during normal install sequence after initial timeout.
             */
            if (!_this.versionChecked && newConnectStatus === constants_1.STATUS.RUNNING) {
                return _this.checkVersion();
            }
            // Workaround for weird safari extension detector logic. We don't want to go to
            // running from outdated unless it's from a version check. ASCN-2271.
            if (Utils.BROWSER.SAFARI &&
                _this.connectStatus === constants_1.STATUS.OUTDATED &&
                newConnectStatus === constants_1.STATUS.RUNNING) {
                return _this.checkVersion();
            }
            // Avoid duplicate event notifications
            if (_this.connectStatus === newConnectStatus) {
                return;
            }
            Logger.debug('[' + _this._objectId + '] Request handler status changing from[' + _this.connectStatus
                + '] to[' + newConnectStatus + ']');
            _this.connectStatus = newConnectStatus;
            if (_this.connectStatus === constants_1.STATUS.RUNNING) {
                _this.processQueue();
            }
            // Check for status internal to request handler
            if (_this._handlerStatus === constants_1.STATUS.DEGRADED) {
                return _this.checkVersion(); // Attempt to reconnect
            }
            // these are handler states - don't bubble up to Connect
            if (newConnectStatus === constants_1.STATUS.WAITING || newConnectStatus === constants_1.STATUS.STOPPED) {
                return;
            }
            _this._options.statusListener(_this.connectStatus);
        };
        /**
         * Verify Connect version meets minimum version requirements
         */
        this.checkVersionCallback = function (response) {
            _this.versionChecked = true;
            delete _this._idRequestHash[response.requestId];
            if (Utils.isSuccessCode(response.status)) {
                var parsedResponse = Utils.parseJson(response.body);
                if (Utils.isError(parsedResponse)) {
                    Logger.error('Failed to parse version response: ' + response);
                    return;
                }
                else {
                    globals_1.ConnectGlobals.connectVersion = parsedResponse.version;
                }
            }
            else if (response.status === 0) {
                Logger.debug('Bad check version response. Retrying...');
                /** Keep trying to check version until connection to the server resumes */
                _this.versionChecked = false;
                setTimeout(function () {
                    void _this.checkVersion();
                }, 500);
                return;
            }
            if (!Utils.checkVersionException()) {
                if (_this._options.minVersion === '' || Utils.versionLessThan(_this._options.minVersion, constants_1.MIN_SECURE_VERSION)) {
                    _this._options.minVersion = constants_1.MIN_SECURE_VERSION;
                }
                if (Utils.versionLessThan(globals_1.ConnectGlobals.connectVersion, _this._options.minVersion)) {
                    /** Check if already in the outdated state. Don't want to notify */
                    /**  event listeners of same status and calling require multiple times. */
                    if (_this.connectStatus !== constants_1.STATUS.OUTDATED) {
                        _this.changeConnectStatus(constants_1.STATUS.OUTDATED);
                        /** Trigger update interface in Connect */
                        var requestId = _this._nextId++;
                        var postData = { min_version: _this._options.minVersion, sdk_location: _this._options.sdkLocation };
                        var endpoint = {
                            method: 'POST',
                            path: '/connect/update/require',
                            body: JSON.stringify(postData)
                        };
                        _this.cacheRequest(endpoint, requestId);
                        void _this._strategy.httpRequest(endpoint, requestId);
                    }
                    // Since Connect is outdated, go into a version detection loop
                    var attemptNumber_1 = 1;
                    var check = function () {
                        Logger.debug('Checking for Connect upgrade. Attempt ' + attemptNumber_1);
                        if (Utils.BROWSER.SAFARI) {
                            Logger.debug('Safari upgrade requires a page refresh. Extension context becomes invalidated.');
                        }
                        attemptNumber_1++;
                        if (_this.connectStatus !== constants_1.STATUS.RUNNING && _this._handlerStatus !== constants_1.STATUS.STOPPED) {
                            var endpoint = {
                                method: 'GET',
                                path: '/connect/info/version'
                            };
                            var requestId = _this._nextId++;
                            _this._strategy.httpRequest(endpoint, requestId).then(function (response) {
                                var waitUpgradeResponse = Utils.parseJson(response.body);
                                // TODO: Remove duplication here
                                if (Utils.isError(waitUpgradeResponse)) {
                                    Logger.error('Failed to parse version response: ' + response);
                                    return;
                                }
                                if (!Utils.versionLessThan(waitUpgradeResponse.version, _this._options.minVersion)) {
                                    Logger.debug('Updated Connect found.');
                                    clearInterval(connectVersionRetry_1);
                                    // Go back to running state
                                    void _this.checkVersion();
                                }
                            }).catch(function (error) {
                                throw new Error(error);
                            });
                        }
                        else {
                            // If Connect is running, we shouldn't be calling this anymore
                            clearInterval(connectVersionRetry_1);
                        }
                    };
                    // Triggers version check until version response satisfies min version requirement
                    var connectVersionRetry_1 = setInterval(check, 1000);
                    return;
                }
            }
            _this.changeConnectStatus(constants_1.STATUS.RUNNING);
        };
        /**
         * Helper function to add request to internal cache for request tracking
         */
        this.cacheRequest = function (endpoint, requestId, promiseInfo) {
            var requestInfo = {
                method: endpoint.method,
                path: endpoint.path,
                body: endpoint.body,
                requestId: requestId
            };
            if (promiseInfo) {
                requestInfo.resolve = promiseInfo.resolve;
                requestInfo.reject = promiseInfo.reject;
            }
            _this._idRequestHash[requestId] = requestInfo;
            return requestInfo;
        };
        /**
         * Get Connect version and enforce version requirements
         */
        this.checkVersion = function () { return tslib_1.__awaiter(_this, void 0, void 0, function () {
            var endpoint, requestId, response;
            return tslib_1.__generator(this, function (_a) {
                switch (_a.label) {
                    case 0:
                        endpoint = {
                            method: 'GET',
                            path: '/connect/info/version'
                        };
                        requestId = this._nextId++;
                        this.cacheRequest(endpoint, requestId);
                        return [4 /*yield*/, this._strategy.httpRequest(endpoint, requestId)];
                    case 1:
                        response = _a.sent();
                        if (response) {
                            this.checkVersionCallback(response);
                        }
                        return [2 /*return*/];
                }
            });
        }); };
        /** Promise that resolves successful client requests. */
        this.handleResponse = function (response) {
            return new Promise(function (resolve, reject) {
                /** Implementation handler might handle this case already */
                if (_this._handlerStatus === constants_1.STATUS.STOPPED) {
                    Logger.debug('Connect stopped. Skipping request processing.');
                    return reject(Utils.createError(-1, 'Connect is stopped. Skipping request processing.'));
                }
                var requestInfo = _this._idRequestHash[response.requestId];
                if (response.status === 0) {
                    if (_this._pollingRequestErrors < constants_1.MAX_POLLING_ERRORS &&
                        requestInfo.path.indexOf('/connect/transfers/activity') > 0) {
                        _this._pollingRequestErrors++;
                        return reject(Utils.createError(-1, 'Error processing transfer activity request'));
                    }
                    /** This was a client request, so queue it for processing later. */
                    _this._queue.push(requestInfo);
                    return;
                }
                if (_this.connectStatus !== constants_1.STATUS.RUNNING) {
                    _this.changeConnectStatus(constants_1.STATUS.RUNNING);
                }
                var parsedResponse = Utils.parseJson(response.body);
                delete _this._idRequestHash[response.requestId];
                // Reject if response has error fields or if status code is not 2xx
                if (Utils.isError(parsedResponse) || !Utils.isSuccessCode(response.status)) {
                    reject(parsedResponse);
                }
                else {
                    resolve(parsedResponse);
                }
            });
        };
        this.start = function (endpoint) {
            return new Promise(function (resolve, reject) {
                if (_this._handlerStatus === constants_1.STATUS.STOPPED) {
                    return reject(Utils.createError(-1, 'Connect is stopped. Call #start to resume session.'));
                }
                if (_this._handlerStatus === constants_1.STATUS.DEGRADED) {
                    return _this.checkVersion(); // Attempt to reconnect
                }
                var requestId = _this._nextId++;
                var requestInfo = _this.cacheRequest(endpoint, requestId, { resolve: resolve, reject: reject });
                /**
                 * If Connect is not ready, queue the client request and resolve the
                 * request when the queue is processed.
                 */
                if (_this.connectStatus !== constants_1.STATUS.RUNNING) {
                    Logger.debug("Queueing request. Connect is currently " + _this.connectStatus + ".");
                    _this._queue.push(requestInfo);
                    return;
                }
                _this._strategy.httpRequest(endpoint, requestId)
                    .then(function (response) {
                    return _this.handleResponse(response);
                })
                    .then(function (response) {
                    resolve(response);
                })
                    .catch(function (error) {
                    reject(error);
                });
            });
        };
        this.handleFallback = function (response) {
            if (response.status === 0) {
                return;
            }
            var parsedResponse = Utils.parseJson(response.body);
            delete _this._idRequestHash[response.requestId];
            if (Utils.isError(parsedResponse)) {
                return;
            }
            else {
                return parsedResponse;
            }
        };
        this.stopRequests = function () {
            _this._handlerStatus = constants_1.STATUS.STOPPED;
            if (typeof _this._strategy.stop === 'function') {
                _this._strategy.stop();
            }
            return true;
        };
        this._provider = new provider_1.default(tslib_1.__assign({}, _options, { requestStatusCallback: this.changeConnectStatus }));
        this._objectId = _options.objectId;
        this._nextId = this._objectId * 10000;
        /** Setup continue event listener */
        window.addEventListener('message', function (evt) {
            if (_this.connectStatus !== constants_1.STATUS.RUNNING && evt.data === 'continue') {
                if (_this._strategy) {
                    if (_this._strategy.stop) {
                        _this._strategy.stop();
                        _this._strategy = _this._provider.getHttpStrategy();
                    }
                }
            }
        });
    }
    /**
     * Send version or ping requests via the http strategy for debugging.
     */
    RequestHandler.prototype.httpFallback = function (api) {
        return tslib_1.__awaiter(this, void 0, void 0, function () {
            var httpFallback, endpoint, requestId, response;
            return tslib_1.__generator(this, function (_a) {
                switch (_a.label) {
                    case 0:
                        httpFallback = this._provider.getHttpStrategy();
                        endpoint = {
                            path: '/connect/info/' + api,
                            method: 'GET'
                        };
                        requestId = this._nextId++;
                        this.cacheRequest(endpoint, requestId);
                        return [4 /*yield*/, httpFallback.httpRequest(endpoint, requestId)];
                    case 1:
                        response = _a.sent();
                        return [2 /*return*/, this.handleFallback(response)];
                }
            });
        });
    };
    /** Define timeout behavior */
    RequestHandler.prototype.handleTimeout = function (timeout) {
        return tslib_1.__awaiter(this, void 0, void 0, function () {
            var response;
            return tslib_1.__generator(this, function (_a) {
                switch (_a.label) {
                    case 0:
                        /**
                         * Return error message from strategy. Otherwise do some debugging first.
                         */
                        if (timeout.error.user_message !== 'timeout') {
                            return [2 /*return*/, Promise.reject(new Error("Reason: " + timeout.error.user_message))];
                        }
                        if (this._handlerStatus === constants_1.STATUS.STOPPED) {
                            return [2 /*return*/, Promise.reject(new Error('Reason: stop() was called during initialization.'))];
                        }
                        if (!(this.connectStatus !== constants_1.STATUS.RUNNING && this.connectStatus !== constants_1.STATUS.OUTDATED && this.connectStatus !== constants_1.STATUS.EXTENSION_INSTALL)) return [3 /*break*/, 3];
                        Logger.debug("Connect detection timed out after: " + this._options.connectLaunchWaitTimeoutMs + "ms");
                        if (this._strategy.name === 'http' || this._strategy.name === 'safari' || this._strategy.name === 'npapi' || this._strategy.name === 'nmh') {
                            this.changeConnectStatus(constants_1.STATUS.FAILED);
                        }
                        if (!(this._strategy.name === 'nmh')) return [3 /*break*/, 2];
                        if (!(this.connectStatus === constants_1.STATUS.FAILED)) return [3 /*break*/, 2];
                        return [4 /*yield*/, this.httpFallback('version')];
                    case 1:
                        response = _a.sent();
                        if (response && Utils.versionLessThan(response.version, '3.9')) {
                            return [2 /*return*/, Promise.reject(new Error('Reason: Incompatible version of Connect detected. You must upgrade to 3.9+.'))];
                        }
                        else if (response && !Utils.versionLessThan(response.version, '3.9')) {
                            return [2 /*return*/, Promise.reject(new Error('Reason: Connect 3.9+ was detected and is responding to http requests, but not to extension requests. Check native message host registration.'))];
                        }
                        else {
                            return [2 /*return*/, Promise.reject(new Error('Reason: Check that Connect 3.9+ is installed.'))];
                        }
                        _a.label = 2;
                    case 2: 
                    /** Generic timeout error */
                    return [2 /*return*/, Promise.reject(new Error("Reason: " + this._strategy.name + " init timeout"))];
                    case 3:
                        if (this.connectStatus === constants_1.STATUS.EXTENSION_INSTALL) {
                            return [2 /*return*/, Promise.reject(new Error('Reason: Extension not detected. Make sure it is enabled if already installed.'))];
                        }
                        /** Connect is detected but outdated. */
                        if (this.connectStatus === constants_1.STATUS.OUTDATED) {
                            Logger.debug('Connect detected but is outdated.');
                            return [2 /*return*/];
                        }
                        return [2 /*return*/];
                }
            });
        });
    };
    /**
     * Select request implementation and initialize Connect
     */
    RequestHandler.prototype.init = function () {
        return tslib_1.__awaiter(this, void 0, void 0, function () {
            var _a, timeoutPromise, timeout;
            var _this = this;
            return tslib_1.__generator(this, function (_b) {
                switch (_b.label) {
                    case 0:
                        /** Reset Connect and handler statuses */
                        this.changeConnectStatus(constants_1.STATUS.INITIALIZING);
                        this._handlerStatus = '';
                        /** Await implementation selection */
                        Logger.debug('Determining request strategy...');
                        _a = this;
                        return [4 /*yield*/, this._provider.getStrategy()];
                    case 1:
                        _a._strategy = _b.sent();
                        timeoutPromise = new Promise(function (reject) {
                            setTimeout(reject, _this._options.connectLaunchWaitTimeoutMs, Utils.createError(-1, 'timeout'));
                        });
                        return [4 /*yield*/, Promise.race([
                                timeoutPromise,
                                this._strategy.startup()
                            ])];
                    case 2:
                        timeout = _b.sent();
                        if (Utils.isError(timeout)) {
                            return [2 /*return*/, this.handleTimeout(timeout)];
                        }
                        Logger.debug('Connect initialized. Checking version now.');
                        /** Ensure Connect meets version requirements */
                        return [4 /*yield*/, this.checkVersion()];
                    case 3:
                        /** Ensure Connect meets version requirements */
                        _b.sent();
                        return [2 /*return*/];
                }
            });
        });
    };
    return RequestHandler;
}());
exports.default = RequestHandler;


/***/ }),

/***/ "./src/request/strategy/extension/base-ext.ts":
/*!****************************************************!*\
  !*** ./src/request/strategy/extension/base-ext.ts ***!
  \****************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
var utils_1 = __webpack_require__(/*! ../../../utils */ "./src/utils.ts");
var constants_1 = __webpack_require__(/*! ../../../constants */ "./src/constants/index.ts");
var logger_1 = __webpack_require__(/*! ../../../logger */ "./src/logger.ts");
/*
 * Abstract base class that holds all code that is common to any extension
 * request handler.
 */
var BaseExtensionStrategy = /** @class */ (function () {
    function BaseExtensionStrategy(options) {
        var _this = this;
        /** Internal cache for active requests */
        this.outstandingRequests = {};
        this.connectStatus = constants_1.STATUS.INITIALIZING;
        /** Default dialog timeout (24 hours) */
        this.defaultDialogTimeout = 86400000;
        this.changeConnectStatus = function (newConnectStatus) {
            if (_this.connectStatus === newConnectStatus) {
                return;
            }
            _this.connectStatus = newConnectStatus;
            _this.options.requestStatusCallback(newConnectStatus);
        };
        this.httpRequest = function (endpoint, requestId) {
            var requestPromise = utils_1.generatePromiseData();
            if (endpoint.path.indexOf('/v5/') > -1 || endpoint.path.indexOf('/v6/') > -1) {
                // TODO: Don't mutate original object
                endpoint.path = endpoint.path.replace('/v5', '').replace('/v6', '');
            }
            // Safari extension doesn't accept undefined data even if it is a GET request
            if (utils_1.isNullOrUndefinedOrEmpty(endpoint.body)) {
                endpoint.body = '';
            }
            var req = {
                'request_id': requestId,
                'min_version': _this.options.minVersion || '',
                'method': endpoint.method,
                'uri_reference': endpoint.path,
                'body': endpoint.body
            };
            /**
             * If it's a dialog api and user sets a request timeout, set the request timeout here.
             * Otherwise, use default 24 hour timeout.
             */
            if (/select/i.test(endpoint.path)) {
                if (_this.options.extensionRequestTimeout) {
                    req.timeout = _this.options.extensionRequestTimeout;
                }
                else {
                    req.timeout = _this.defaultDialogTimeout;
                }
            }
            logger_1.debug(req);
            _this.outstandingRequests[requestId] = {
                'req': req,
                'response': '',
                'resolve': requestPromise.resolver
            };
            // TODO: Validate the data length is not over 100MB
            document.dispatchEvent(new CustomEvent('AsperaConnectRequest', { 'detail': req }));
            return requestPromise.promise;
        };
        this.options = options;
    }
    return BaseExtensionStrategy;
}());
exports.default = BaseExtensionStrategy;


/***/ }),

/***/ "./src/request/strategy/extension/index.ts":
/*!*************************************************!*\
  !*** ./src/request/strategy/extension/index.ts ***!
  \*************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
var tslib_1 = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
var nmh_1 = tslib_1.__importDefault(__webpack_require__(/*! ./nmh */ "./src/request/strategy/extension/nmh.ts"));
exports.NativeHostStrategy = nmh_1.default;
var safari_1 = tslib_1.__importDefault(__webpack_require__(/*! ./safari */ "./src/request/strategy/extension/safari.ts"));
exports.SafariAppStrategy = safari_1.default;


/***/ }),

/***/ "./src/request/strategy/extension/nmh.ts":
/*!***********************************************!*\
  !*** ./src/request/strategy/extension/nmh.ts ***!
  \***********************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
var tslib_1 = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
var Logger = tslib_1.__importStar(__webpack_require__(/*! ../../../logger */ "./src/logger.ts"));
var utils_1 = __webpack_require__(/*! ../../../utils */ "./src/utils.ts");
var constants_1 = __webpack_require__(/*! ../../../constants */ "./src/constants/index.ts");
var base_ext_1 = tslib_1.__importDefault(__webpack_require__(/*! ./base-ext */ "./src/request/strategy/extension/base-ext.ts"));
var NativeHostStrategy = /** @class */ (function (_super) {
    tslib_1.__extends(NativeHostStrategy, _super);
    function NativeHostStrategy() {
        var _this = _super !== null && _super.apply(this, arguments) || this;
        _this._extensionDetected = false;
        _this.name = 'nmh';
        /**
         * Handles disconnect messages from the extension.
         */
        _this.handleDisconnect = function (evt) { return tslib_1.__awaiter(_this, void 0, void 0, function () {
            var installIssueDetected_1, connectFound;
            return tslib_1.__generator(this, function (_a) {
                switch (_a.label) {
                    case 0:
                        Logger.debug('Native host disconnected. Detail: ' + evt.detail);
                        /** Disconnect is expected if Connect is outdated */
                        if (this.connectStatus === constants_1.STATUS.OUTDATED) {
                            return [2 /*return*/];
                        }
                        if (evt && evt.detail) {
                            installIssueDetected_1 = false;
                            [
                                'native messaging host not found',
                                'Error when communicating with the native messaging host',
                                'Access to the specified native messaging host is forbidden',
                                'No such native application' // Firefox
                            ].forEach(function (message) {
                                if (evt.detail.indexOf(message) !== -1) {
                                    installIssueDetected_1 = true;
                                }
                            });
                            if (installIssueDetected_1) {
                                this.changeConnectStatus(constants_1.STATUS.FAILED);
                                document.removeEventListener('AsperaConnectDisconnect', this.handleDisconnect);
                                return [2 /*return*/];
                            }
                        }
                        if (!(this.connectStatus !== constants_1.STATUS.DEGRADED)) return [3 /*break*/, 2];
                        this.changeConnectStatus(constants_1.STATUS.DEGRADED);
                        return [4 /*yield*/, this.detectConnect(this.options.connectLaunchWaitTimeoutMs)];
                    case 1:
                        connectFound = _a.sent();
                        if (connectFound) {
                            this.changeConnectStatus(constants_1.STATUS.RUNNING);
                        }
                        else {
                            this.changeConnectStatus(constants_1.STATUS.FAILED);
                        }
                        _a.label = 2;
                    case 2: return [2 /*return*/];
                }
            });
        }); };
        /**
         * Resolves the extension response
         */
        _this.resolveExtensionResponse = function (evt) {
            var data;
            if (evt.type === 'message' &&
                typeof evt.data === 'object' &&
                'type' in evt.data &&
                evt.data.type === 'AsperaConnectResponse' &&
                'detail' in evt.data) {
                data = evt.data.detail;
            }
            else if ('detail' in evt) {
                /**
                 * CustomEvent interface used in disconnect event
                 */
                // @ts-ignore
                data = evt.detail;
            }
            if (data) {
                Logger.trace('Native host impl received response: ' + JSON.stringify(data));
                var id = data.request_id;
                /**
                 * Each instance of this class will receive document events, but
                 * the request might not have originated from this instance.
                 */
                if (!(id in _this.outstandingRequests)) {
                    return;
                }
                var resolve = _this.outstandingRequests[id].resolve;
                if ('body64' in data) {
                    _this.outstandingRequests[id].response += data.body64;
                    if (data.complete === true) {
                        var resp = utils_1.atou(_this.outstandingRequests[id].response);
                        delete _this.outstandingRequests[id];
                        resolve({
                            status: data.status,
                            body: resp,
                            requestId: id
                        });
                    }
                }
                else {
                    delete _this.outstandingRequests[id];
                    resolve({
                        status: data.status,
                        body: data.body,
                        requestId: id
                    });
                }
            }
        };
        _this.detectionLoop = function (timeoutMs, loop) {
            if (timeoutMs === void 0) { timeoutMs = -1; }
            return tslib_1.__awaiter(_this, void 0, void 0, function () {
                var timeoutPromise, found;
                return tslib_1.__generator(this, function (_a) {
                    switch (_a.label) {
                        case 0:
                            timeoutPromise = new Promise(function (resolve) {
                                setTimeout(resolve, timeoutMs, false);
                            });
                            return [4 /*yield*/, Promise.race(tslib_1.__spread(timeoutMs !== -1 ? [timeoutPromise] : [], [
                                    loop()
                                ]))];
                        case 1:
                            found = _a.sent();
                            clearInterval(this._detectionRetry);
                            return [2 /*return*/, found];
                    }
                });
            });
        };
        /**
         * Returns promise that resolves with true | false if Connect is detected or not.
         */
        _this.detectConnect = function (timeoutMs) {
            if (timeoutMs === void 0) { timeoutMs = -1; }
            return tslib_1.__awaiter(_this, void 0, void 0, function () {
                var waitUntilDetected;
                var _this = this;
                return tslib_1.__generator(this, function (_a) {
                    waitUntilDetected = function () {
                        return new Promise(function (resolve) {
                            var attemptNumber = 1;
                            var check = function () { return tslib_1.__awaiter(_this, void 0, void 0, function () {
                                var endpoint, detectConnectRequestId, status_1, error_1;
                                return tslib_1.__generator(this, function (_a) {
                                    switch (_a.label) {
                                        case 0:
                                            _a.trys.push([0, 2, , 3]);
                                            Logger.debug('Detecting Connect installation via extension. Attempt ' + attemptNumber);
                                            attemptNumber++;
                                            endpoint = {
                                                method: 'GET',
                                                path: '/connect/info/version'
                                            };
                                            detectConnectRequestId = this.options.objectId * 10500;
                                            return [4 /*yield*/, this.httpRequest(endpoint, detectConnectRequestId)];
                                        case 1:
                                            status_1 = (_a.sent()).status;
                                            if (status_1 === 503) {
                                                Logger.debug('Detected old version of Connect via extension.');
                                                this.changeConnectStatus(constants_1.STATUS.OUTDATED);
                                            }
                                            else {
                                                Logger.debug('Detected Connect installation via extension.');
                                                utils_1.recordConnectDetected();
                                                /** Go to running here if Connect was installed during loop after initial timeout */
                                                this.changeConnectStatus(constants_1.STATUS.RUNNING);
                                                clearInterval(this._detectionRetry);
                                                resolve(true);
                                            }
                                            return [3 /*break*/, 3];
                                        case 2:
                                            error_1 = _a.sent();
                                            /** If there was an error, avoid infinitely retrying */
                                            clearInterval(this._detectionRetry);
                                            resolve(false);
                                            return [3 /*break*/, 3];
                                        case 3: return [2 /*return*/];
                                    }
                                });
                            }); };
                            _this._detectionRetry = setInterval(check, 1000);
                            /** Call check() directly to avoid waiting the initial 1 second */
                            void check();
                        });
                    };
                    return [2 /*return*/, this.detectionLoop(timeoutMs, waitUntilDetected)];
                });
            });
        };
        /**
         * Returns promise that resolves with true | false if extension is detected or not.
         */
        _this.detectExtension = function (timeoutMs) {
            if (timeoutMs === void 0) { timeoutMs = -1; }
            return tslib_1.__awaiter(_this, void 0, void 0, function () {
                var waitUntilDetected;
                var _this = this;
                return tslib_1.__generator(this, function (_a) {
                    if (this._extensionDetected) {
                        return [2 /*return*/, true];
                    }
                    waitUntilDetected = function () {
                        return new Promise(function (resolve) {
                            var attemptNumber = 1;
                            var check = function () {
                                Logger.debug('Detecting Connect extension. Attempt ' + attemptNumber);
                                attemptNumber++;
                                document.dispatchEvent(new CustomEvent('AsperaConnectCheck', {}));
                            };
                            var versionResponse = function (evt) {
                                if (evt.type === 'message' && typeof evt.data === 'object' && 'type' in evt.data
                                    && evt.data.type === 'AsperaConnectCheckResponse') {
                                    window.removeEventListener('message', versionResponse);
                                    Logger.debug('Extension detected: ' + JSON.stringify(evt.data));
                                    _this._extensionDetected = true;
                                    clearInterval(_this._detectionRetry);
                                    resolve(true);
                                }
                            };
                            window.addEventListener('message', versionResponse);
                            var interval = timeoutMs === -1 ? 1000 : 200;
                            _this._detectionRetry = setInterval(check, interval);
                            void check();
                        });
                    };
                    return [2 /*return*/, this.detectionLoop(timeoutMs, waitUntilDetected)];
                });
            });
        };
        _this.stop = function () {
            clearInterval(_this._detectionRetry);
        };
        /**
         * Returns only once extension and Connect are both detected. Caller handles
         * any timeout.
         */
        _this.startup = function () { return tslib_1.__awaiter(_this, void 0, void 0, function () {
            return tslib_1.__generator(this, function (_a) {
                switch (_a.label) {
                    case 0:
                        /** Setup extension response handlers */
                        // @ts-ignore
                        document.addEventListener('AsperaConnectResponse', this.resolveExtensionResponse);
                        window.addEventListener('message', this.resolveExtensionResponse);
                        /** Register disconnect handler before init to handle native host not found issues during install */
                        document.addEventListener('AsperaConnectDisconnect', this.handleDisconnect);
                        /** Await extension detection */
                        return [4 /*yield*/, this.detectExtension()];
                    case 1:
                        /** Await extension detection */
                        _a.sent();
                        /** Await Connect detection */
                        return [4 /*yield*/, this.detectConnect()];
                    case 2:
                        /** Await Connect detection */
                        _a.sent();
                        Logger.debug('nmh init finished');
                        return [2 /*return*/];
                }
            });
        }); };
        return _this;
    }
    return NativeHostStrategy;
}(base_ext_1.default));
exports.default = NativeHostStrategy;


/***/ }),

/***/ "./src/request/strategy/extension/safari.ts":
/*!**************************************************!*\
  !*** ./src/request/strategy/extension/safari.ts ***!
  \**************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
var tslib_1 = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
var Logger = tslib_1.__importStar(__webpack_require__(/*! ../../../logger */ "./src/logger.ts"));
var constants_1 = __webpack_require__(/*! ../../../constants */ "./src/constants/index.ts");
var base_ext_1 = tslib_1.__importDefault(__webpack_require__(/*! ./base-ext */ "./src/request/strategy/extension/base-ext.ts"));
var MAX_POLLING_ERRORS = 3;
var SafariAppStrategy = /** @class */ (function (_super) {
    tslib_1.__extends(SafariAppStrategy, _super);
    function SafariAppStrategy() {
        var _this = _super !== null && _super.apply(this, arguments) || this;
        _this.pollingRequestErrors = 0;
        _this.extensionDetected = false;
        _this.name = 'safari';
        /**
         * Resolves the http response
         */
        _this.resolveHttpResponse = function (evt) {
            if (evt.detail) {
                Logger.trace('Safari extension impl received response: ' + JSON.stringify(evt));
                var id = evt.detail.request_id;
                /**
                 * Each instance of this class will receive document events, but
                 * the request might not have originated from this instance.
                 */
                if (!(id in _this.outstandingRequests)) {
                    return;
                }
                var path = _this.outstandingRequests[id].req.uri_reference;
                var resolve = _this.outstandingRequests[id].resolve;
                delete _this.outstandingRequests[id];
                if (evt.detail.status === 0 && path.indexOf('/connect/transfers/activity') > 0
                    && _this.pollingRequestErrors < MAX_POLLING_ERRORS) {
                    _this.pollingRequestErrors++;
                    return;
                }
                else {
                    _this.pollingRequestErrors = 0;
                    resolve({
                        status: evt.detail.status,
                        body: evt.detail.body,
                        requestId: id
                    });
                }
            }
        };
        _this.checkEvent = function () {
            document.dispatchEvent(new CustomEvent('AsperaConnectCheck', {}));
        };
        _this.detectExtension = function (timeoutMs) {
            if (timeoutMs === void 0) { timeoutMs = -1; }
            return tslib_1.__awaiter(_this, void 0, void 0, function () {
                var timeoutPromise, waitUntilDetected, found;
                var _this = this;
                return tslib_1.__generator(this, function (_a) {
                    switch (_a.label) {
                        case 0:
                            /** First check if we have already detected the extension */
                            if (this.extensionDetected) {
                                Logger.debug('Skipping extension check - already detected.');
                                return [2 /*return*/, true];
                            }
                            timeoutPromise = new Promise(function (resolve) {
                                setTimeout(resolve, timeoutMs, false);
                            });
                            waitUntilDetected = function () {
                                return new Promise(function (resolve) {
                                    var attemptNumber = 1;
                                    var check = function () {
                                        Logger.debug('Detecting Connect extension. Attempt ' + attemptNumber);
                                        attemptNumber++;
                                        // Event based
                                        _this.checkEvent();
                                        // DOM based extension detector
                                        var connectDetected = document.getElementById('aspera-connect-detector');
                                        if (connectDetected) {
                                            var extensionEnable = connectDetected.getAttribute('extension-enable');
                                            if (extensionEnable === 'true') {
                                                Logger.debug('Detected extension');
                                                clearInterval(_this.detectionRetry);
                                                // Additional check to see if connect check is responding
                                                _this.checkEvent();
                                                // wait for connect check response for 1 second
                                                setTimeout(function () {
                                                    if (!_this.extensionDetected) {
                                                        window.postMessage('show_safari_mitigate', '*');
                                                        resolve(false);
                                                    }
                                                    else {
                                                        /** Go to running here if Connect was installed during loop after initial timeout */
                                                        _this.changeConnectStatus(constants_1.STATUS.RUNNING);
                                                        resolve(true);
                                                    }
                                                }, 1000);
                                            }
                                        }
                                        // create detector
                                        if (!connectDetected) {
                                            Logger.debug('Creating detector in sdk...');
                                            var div = document.createElement('div');
                                            div.id = 'aspera-connect-detector';
                                            div.setAttribute('extension-enable', 'false');
                                            document.body.appendChild(div);
                                        }
                                    };
                                    // NOTE: Safari bugs sometime leads to breakdown in getting responses
                                    var versionResponse = function (evt) {
                                        if (evt.type === 'AsperaConnectCheckResponse' && 'detail' in evt && typeof evt.detail === 'object') {
                                            document.removeEventListener('AsperaConnectCheckResponse', versionResponse);
                                            Logger.debug('Got response from Connect: ' + JSON.stringify(evt.detail));
                                            clearInterval(_this.detectionRetry);
                                            _this.extensionDetected = true;
                                            resolve(true);
                                        }
                                    };
                                    document.addEventListener('AsperaConnectCheckResponse', versionResponse);
                                    var interval = timeoutMs === -1 ? 500 : 200;
                                    _this.detectionRetry = setInterval(check, interval);
                                    check();
                                });
                            };
                            return [4 /*yield*/, Promise.race(tslib_1.__spread(timeoutMs !== -1 ? [timeoutPromise] : [], [
                                    waitUntilDetected()
                                ]))];
                        case 1:
                            found = _a.sent();
                            clearInterval(this.detectionRetry);
                            return [2 /*return*/, found];
                    }
                });
            });
        };
        _this.stop = function () {
            clearTimeout(_this.detectionRetry);
        };
        _this.startup = function () { return tslib_1.__awaiter(_this, void 0, void 0, function () {
            return tslib_1.__generator(this, function (_a) {
                switch (_a.label) {
                    case 0:
                        Logger.debug('startup()');
                        // @ts-ignore
                        document.addEventListener('AsperaConnectResponse', this.resolveHttpResponse);
                        /** Await extension detection */
                        return [4 /*yield*/, this.detectExtension()];
                    case 1:
                        /** Await extension detection */
                        _a.sent();
                        Logger.debug('safari init finished');
                        return [2 /*return*/];
                }
            });
        }); };
        return _this;
    }
    SafariAppStrategy.prototype.triggerExtensionCheck = function () {
        var dummyIframe = document.createElement('IFRAME');
        dummyIframe.src = 'fasp://initialize?checkextensions';
        dummyIframe.style.visibility = 'hidden';
        dummyIframe.style.position = 'absolute';
        dummyIframe.style.width = '0px';
        dummyIframe.style.height = '0px';
        dummyIframe.style.border = '0px';
        document.body.appendChild(dummyIframe);
    };
    return SafariAppStrategy;
}(base_ext_1.default));
exports.default = SafariAppStrategy;


/***/ }),

/***/ "./src/request/strategy/http/http.ts":
/*!*******************************************!*\
  !*** ./src/request/strategy/http/http.ts ***!
  \*******************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
var tslib_1 = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
var browser_1 = tslib_1.__importDefault(__webpack_require__(/*! ../../../helpers/browser */ "./src/helpers/browser.ts"));
var Logger = tslib_1.__importStar(__webpack_require__(/*! ../../../logger */ "./src/logger.ts"));
var Utils = tslib_1.__importStar(__webpack_require__(/*! ../../../utils */ "./src/utils.ts"));
var constants_1 = __webpack_require__(/*! ../../../constants */ "./src/constants/index.ts");
var HttpStrategy = /** @class */ (function () {
    function HttpStrategy(options) {
        var _this = this;
        this.options = options;
        // private initCallback: any;
        this.connectStatus = constants_1.STATUS.INITIALIZING;
        /** Internal tracker for port Connect is listening on */
        this.connectPort = constants_1.DEFAULT_PORT;
        /** Timeout used when iterating ports */
        this.scanRetryTimeValues = [0, 1];
        /** Debugging variable to keep track of multiple instances */
        this.objectId = 0;
        /** Simple counter to increment request ids */
        this.nextId = 0;
        /** Track number of polling errors for debounce */
        this.pollingRequestErrors = 0;
        this.windowUnloading = false;
        this.VERSION_PREFIX = '/v5';
        this.name = 'http';
        /** Track http implementation state */
        this.changeConnectStatus = function (newConnectStatus) {
            if (_this.connectStatus === newConnectStatus) {
                return;
            }
            Logger.debug('[' + _this.objectId + '] Http request handler status changing from[' + _this.connectStatus
                + '] to[' + newConnectStatus + ']');
            _this.connectStatus = newConnectStatus;
            if (_this.requestStatusCallback) {
                _this.requestStatusCallback(newConnectStatus);
            }
        };
        /**
         * Iterates through ports and returns true if Connect responds
         */
        this.check = function () { return tslib_1.__awaiter(_this, void 0, void 0, function () {
            var success, port, requestId, results;
            return tslib_1.__generator(this, function (_a) {
                switch (_a.label) {
                    case 0:
                        success = false;
                        port = constants_1.DEFAULT_PORT;
                        _a.label = 1;
                    case 1:
                        if (!(port < (constants_1.DEFAULT_PORT + 1 + constants_1.MAX_PORT_SEARCH))) return [3 /*break*/, 4];
                        requestId = this.nextId;
                        this.connectPort = port;
                        return [4 /*yield*/, this.ping(requestId)];
                    case 2:
                        results = _a.sent();
                        if (results && Utils.isSuccessCode(results.status)) {
                            success = true;
                            return [3 /*break*/, 4];
                        }
                        _a.label = 3;
                    case 3:
                        port++;
                        return [3 /*break*/, 1];
                    case 4: return [2 /*return*/, success];
                }
            });
        }); };
        this.detectConnect = function (firstRun) { return tslib_1.__awaiter(_this, void 0, void 0, function () {
            var success, retryTimeS_1;
            var _this = this;
            return tslib_1.__generator(this, function (_a) {
                switch (_a.label) {
                    case 0:
                        if (this.connectStatus === constants_1.STATUS.RUNNING || this.connectStatus === constants_1.STATUS.STOPPED) {
                            return [2 /*return*/, true];
                        }
                        else if (this.connectStatus === constants_1.STATUS.INITIALIZING && !firstRun) {
                            Utils.launchConnect();
                            this.changeConnectStatus(constants_1.STATUS.RETRYING);
                        }
                        return [4 /*yield*/, this.check()];
                    case 1:
                        success = _a.sent();
                        if (!!success) return [3 /*break*/, 3];
                        retryTimeS_1 = this.scanRetryTimeValues[0] + this.scanRetryTimeValues[1];
                        this.scanRetryTimeValues[0] = this.scanRetryTimeValues[1];
                        this.scanRetryTimeValues[1] = retryTimeS_1;
                        return [4 /*yield*/, new Promise(function (resolve) {
                                setTimeout(function () { return tslib_1.__awaiter(_this, void 0, void 0, function () {
                                    return tslib_1.__generator(this, function (_a) {
                                        switch (_a.label) {
                                            case 0: return [4 /*yield*/, this.detectConnect(false)];
                                            case 1:
                                                _a.sent();
                                                /** Go to running here if Connect was installed during loop after initial timeout */
                                                this.changeConnectStatus(constants_1.STATUS.RUNNING);
                                                resolve();
                                                return [2 /*return*/];
                                        }
                                    });
                                }); }, retryTimeS_1 * 1000);
                            })];
                    case 2:
                        _a.sent();
                        _a.label = 3;
                    case 3: return [2 /*return*/, true];
                }
            });
        }); };
        this.ping = function (requestId) { return tslib_1.__awaiter(_this, void 0, void 0, function () {
            var request;
            return tslib_1.__generator(this, function (_a) {
                request = {
                    method: 'GET',
                    path: '/connect/info/ping'
                };
                return [2 /*return*/, this.httpRequest(request, requestId)];
            });
        }); };
        this.reconnect = function () { return tslib_1.__awaiter(_this, void 0, void 0, function () {
            return tslib_1.__generator(this, function (_a) {
                switch (_a.label) {
                    case 0:
                        this.changeConnectStatus(constants_1.STATUS.RETRYING);
                        Utils.launchConnect();
                        return [4 /*yield*/, this.detectConnect(false)];
                    case 1:
                        _a.sent();
                        Logger.debug('Reconnect successful!');
                        this.changeConnectStatus(constants_1.STATUS.RUNNING);
                        return [2 /*return*/];
                }
            });
        }); };
        this.send = function (endpoint, requestId) {
            var requestPromise = Utils.generatePromiseData();
            var xhr = Utils.getXMLHttpRequest();
            xhr.onreadystatechange = function () {
                if (_this.connectStatus === constants_1.STATUS.STOPPED || _this.windowUnloading) {
                    Logger.debug('Connect stopped or page unloading. Skipping xhr processing.');
                    return requestPromise.rejecter(Utils.createError(-1, 'Connect stopped or page unloading. Skipping xhr processing.'));
                }
                if (xhr.readyState !== 4) {
                    return;
                }
                if (xhr.status === 0 && _this.connectStatus === constants_1.STATUS.RUNNING) {
                    /**
                     * Avoid excessive relaunch related to polling failures.
                     * Safari causes CORS failures when new page navigation starts
                     */
                    if (_this.pollingRequestErrors < constants_1.MAX_POLLING_ERRORS && endpoint.path.indexOf('activity') > 0) {
                        _this.pollingRequestErrors++;
                        return;
                    }
                    /** If Connect is running, don't need to iterate over ports */
                    if (endpoint.path.indexOf(_this.connectPort.toString()) === -1 && endpoint.path.indexOf('ping') > 0) {
                        return;
                    }
                    void _this.reconnect();
                }
                var response = xhr.responseText;
                Logger.trace('HttpRequest processed[' + endpoint.path + '] postData[' + endpoint.body +
                    '] status[' + xhr.status + '] response[' + response + ']');
                requestPromise.resolver({
                    status: xhr.status,
                    body: response,
                    requestId: requestId
                });
            };
            xhr.open(endpoint.method, endpoint.path, true);
            if (endpoint.method.toUpperCase() === 'GET') {
                xhr.send();
            }
            else {
                xhr.send(endpoint.body);
            }
            return requestPromise.promise;
        };
        this.httpRequest = function (endpoint, requestId) { return tslib_1.__awaiter(_this, void 0, void 0, function () {
            var fullpath, result;
            return tslib_1.__generator(this, function (_a) {
                switch (_a.label) {
                    case 0:
                        fullpath = "" + constants_1.LOCALHOST + this.connectPort + this.VERSION_PREFIX + endpoint.path;
                        // TODO: Make copy of original request
                        endpoint.path = fullpath;
                        return [4 /*yield*/, this.send(endpoint, requestId)];
                    case 1:
                        result = _a.sent();
                        return [2 /*return*/, result];
                }
            });
        }); };
        this.stop = function () {
            _this.changeConnectStatus(constants_1.STATUS.STOPPED);
        };
        this.startup = function () { return tslib_1.__awaiter(_this, void 0, void 0, function () {
            return tslib_1.__generator(this, function (_a) {
                switch (_a.label) {
                    case 0:
                        this.requestStatusCallback = this.options.requestStatusCallback;
                        /** Await Connect detection */
                        return [4 /*yield*/, this.detectConnect(true)];
                    case 1:
                        /** Await Connect detection */
                        _a.sent();
                        Logger.debug('Finished http init');
                        return [2 /*return*/];
                }
            });
        }); };
        // 2018-06-30 Only Safari 12 has trouble with failing XHR requests when page navigation begins
        if (browser_1.default.SAFARI_NO_NPAPI) {
            window.addEventListener('beforeunload', function () {
                _this.windowUnloading = true;
            });
        }
        // Associate request ids with object ids
        if (options.objectId) {
            this.objectId = options.objectId;
            this.nextId = this.objectId * 10000;
        }
    }
    return HttpStrategy;
}());
exports.default = HttpStrategy;


/***/ }),

/***/ "./src/request/strategy/http/index.ts":
/*!********************************************!*\
  !*** ./src/request/strategy/http/index.ts ***!
  \********************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
var tslib_1 = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
var http_1 = tslib_1.__importDefault(__webpack_require__(/*! ./http */ "./src/request/strategy/http/http.ts"));
exports.default = http_1.default;


/***/ }),

/***/ "./src/request/strategy/npapi/index.ts":
/*!*********************************************!*\
  !*** ./src/request/strategy/npapi/index.ts ***!
  \*********************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
var tslib_1 = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
var npapi_1 = tslib_1.__importDefault(__webpack_require__(/*! ./npapi */ "./src/request/strategy/npapi/npapi.ts"));
exports.default = npapi_1.default;


/***/ }),

/***/ "./src/request/strategy/npapi/npapi.ts":
/*!*********************************************!*\
  !*** ./src/request/strategy/npapi/npapi.ts ***!
  \*********************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
var tslib_1 = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
var browser_1 = tslib_1.__importDefault(__webpack_require__(/*! ../../../helpers/browser */ "./src/helpers/browser.ts"));
var constants_1 = __webpack_require__(/*! ../../../constants */ "./src/constants/index.ts");
var Logger = tslib_1.__importStar(__webpack_require__(/*! ../../../logger */ "./src/logger.ts"));
var utils_1 = __webpack_require__(/*! ../../../utils */ "./src/utils.ts");
function mimeType() {
    return 'application/x-aspera-web';
}
var NpapiStrategy = /** @class */ (function () {
    function NpapiStrategy(options) {
        var _this = this;
        this.options = options;
        this.VERSION_PREFIX = '/v5';
        this.pluginId = '';
        this.listenerId = '';
        this.name = 'npapi';
        /*
         * Create the NPAPI plugin <object> element as a child of the DOM element
         * given (if exists)
         *
         * @param {string} initializeTimeout [[AW4.Connect]] instantiation option
         */
        this.createNPAPIPlugin = function (initializeTimeout) {
            var wrapperDiv = document.getElementById(_this.listenerId);
            if (utils_1.isNullOrUndefinedOrEmpty(wrapperDiv)) {
                wrapperDiv = document.createElement('div');
                wrapperDiv.setAttribute('id', _this.listenerId);
                wrapperDiv.setAttribute('style', 'display:inline-block;height:1px;width:1px;');
            }
            else {
                // Remove all elements of the wrapper
                while (wrapperDiv.firstChild) {
                    wrapperDiv.removeChild(wrapperDiv.firstChild);
                }
            }
            _this.npapiPlugin = document.createElement('object');
            _this.npapiPlugin.setAttribute('name', _this.pluginId);
            _this.npapiPlugin.setAttribute('id', _this.pluginId);
            _this.npapiPlugin.setAttribute('type', mimeType());
            _this.npapiPlugin.setAttribute('width', '1');
            _this.npapiPlugin.setAttribute('height', '1');
            var timeoutParam = document.createElement('param');
            timeoutParam.setAttribute('name', 'connect-launch-wait-timeout-ms');
            timeoutParam.setAttribute('value', String(initializeTimeout));
            _this.npapiPlugin.appendChild(timeoutParam);
            wrapperDiv.appendChild(_this.npapiPlugin);
            document.body.appendChild(wrapperDiv);
        };
        /*
         * Place a request for Connect
         *
         * @param {function} callback Function to be called when the request has finished
         * @param {int} requestId Identifier that needs to be returned when calling the given callback
        */
        this.httpRequest = function (endpoint, requestId) {
            var requestPromise = utils_1.generatePromiseData();
            // NPAPI plugin doesn't accept null data even if it is a GET request
            if (utils_1.isNullOrUndefinedOrEmpty(endpoint.body)) {
                endpoint.body = '';
            }
            var fullEndpoint = "" + _this.VERSION_PREFIX + endpoint.path;
            var requestCallback = function (data) {
                /** Parse data to find out if an error ocurred */
                var parsedData = utils_1.parseJson(data);
                if (utils_1.isError(parsedData)) {
                    requestPromise.resolver({
                        status: parsedData.error.code,
                        body: data,
                        requestId: requestId
                    });
                }
                else {
                    requestPromise.resolver({
                        status: 200,
                        body: data,
                        requestId: requestId
                    });
                }
            };
            if (utils_1.isNullOrUndefinedOrEmpty(_this.npapiPlugin)) {
                requestPromise.rejecter(new Error('Plugin not detected.'));
            }
            else {
                _this.npapiPlugin.httpRequestImplementation(endpoint.method, fullEndpoint, endpoint.body, requestCallback);
            }
            return requestPromise.promise;
        };
        /*
         * Called to initialize the plugin, it creates a new instance by appending an
         * <object> element to the DOM and runs the callback with the status
         */
        this.startup = function () { return tslib_1.__awaiter(_this, void 0, void 0, function () {
            var changeConnectStatus, npapiWaitPluginLoadedID_1;
            var _this = this;
            return tslib_1.__generator(this, function (_a) {
                changeConnectStatus = this.options.requestStatusCallback;
                try {
                    if (utils_1.isNullOrUndefinedOrEmpty(this.npapiPlugin)) {
                        if ((browser_1.default.IE && (new ActiveXObject('Aspera.AsperaWebCtrl.1'))) ||
                            mimeType() in navigator.mimeTypes) {
                            this.listenerId = this.options.containerId;
                            this.pluginId = this.options.id;
                            this.createNPAPIPlugin(this.options.connectLaunchWaitTimeoutMs);
                            npapiWaitPluginLoadedID_1 = setInterval(function () {
                                if (!_this.npapiPlugin || !_this.npapiPlugin.queryBuildVersion) {
                                    return;
                                }
                                clearInterval(npapiWaitPluginLoadedID_1);
                                // Check version is correct
                                if (utils_1.versionLessThan(_this.npapiPlugin.queryBuildVersion(), '3.6')) {
                                    // Logger.debug('Plugin too old. Version less than 3.6');
                                    _this.npapiPlugin = undefined;
                                    changeConnectStatus(constants_1.STATUS.FAILED);
                                    return utils_1.createError(-1, 'Plugin too old. Version less than 3.6');
                                }
                                else {
                                    /** Init was successful */
                                    return;
                                }
                            }, 500);
                        }
                        else {
                            // If plugin is still null, it means it is not installed
                            if (utils_1.isNullOrUndefinedOrEmpty(this.npapiPlugin)) {
                                // Logger.debug('Plugin not detected. Either not installed or enabled.');
                                changeConnectStatus(constants_1.STATUS.FAILED);
                                return [2 /*return*/, utils_1.createError(-1, 'Plugin not detected. Either not installed or enabled.')];
                            }
                        }
                    }
                }
                catch (error) {
                    // IE 10 ActiveXObject instantiation error recovery
                    changeConnectStatus(constants_1.STATUS.FAILED);
                    Logger.debug(JSON.stringify(error));
                    return [2 /*return*/, utils_1.createError(-1, "Plugin load error. Make sure plugin is enabled. Details: " + error)];
                }
                return [2 /*return*/];
            });
        }); };
    }
    return NpapiStrategy;
}());
exports.default = NpapiStrategy;


/***/ }),

/***/ "./src/request/strategy/provider.ts":
/*!******************************************!*\
  !*** ./src/request/strategy/provider.ts ***!
  \******************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
var tslib_1 = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
var browser_1 = tslib_1.__importDefault(__webpack_require__(/*! ../../helpers/browser */ "./src/helpers/browser.ts"));
var constants_1 = __webpack_require__(/*! ../../constants */ "./src/constants/index.ts");
var Utils = tslib_1.__importStar(__webpack_require__(/*! ../../utils */ "./src/utils.ts"));
var Logger = tslib_1.__importStar(__webpack_require__(/*! ../../logger */ "./src/logger.ts"));
var installer_1 = __webpack_require__(/*! ../../installer */ "./src/installer.ts");
var npapi_1 = tslib_1.__importDefault(__webpack_require__(/*! ./npapi */ "./src/request/strategy/npapi/index.ts"));
var extension_1 = __webpack_require__(/*! ./extension */ "./src/request/strategy/extension/index.ts");
var http_1 = tslib_1.__importDefault(__webpack_require__(/*! ./http */ "./src/request/strategy/http/index.ts"));
/**
 * Responsible for selecting the appropriate request implementation to
 * use for handling API requests.
 */
var Provider = /** @class */ (function () {
    function Provider(_options) {
        var _this = this;
        this._options = _options;
        this.create = function (klass) {
            return new klass(_this._options);
        };
        this.getHttpStrategy = function () {
            return _this.create(http_1.default);
        };
        this.getStrategy = function () { return tslib_1.__awaiter(_this, void 0, void 0, function () {
            var installed, supportsInstall, e_1;
            return tslib_1.__generator(this, function (_a) {
                switch (_a.label) {
                    case 0:
                        _a.trys.push([0, 5, , 6]);
                        if (!this.supportsNpapi()) return [3 /*break*/, 1];
                        Logger.debug('Using npapi strategy');
                        this.strategy = this.create(npapi_1.default);
                        return [3 /*break*/, 4];
                    case 1:
                        if (!this.requiresHttp()) return [3 /*break*/, 2];
                        this.setHttpStrategy();
                        return [3 /*break*/, 4];
                    case 2:
                        Logger.debug('Attempting extension strategy');
                        this.strategy = this.supportsNativeHost() ? this.create(extension_1.NativeHostStrategy) : this.create(extension_1.SafariAppStrategy);
                        Logger.debug('Checking if extension installed...');
                        return [4 /*yield*/, this.strategy.detectExtension(1000)];
                    case 3:
                        installed = _a.sent();
                        if (!installed) {
                            supportsInstall = installer_1.ConnectInstaller.supportsInstallingExtensions === true;
                            if (this._options.connectMethod === 'extension' || supportsInstall) {
                                if (!this.supportsSafariAppExt()) {
                                    this._options.requestStatusCallback(constants_1.STATUS.EXTENSION_INSTALL);
                                    window.postMessage('show_extension_install', '*');
                                }
                            }
                            else {
                                Logger.debug('Falling back to http strategy');
                                this.setHttpStrategy();
                            }
                        }
                        _a.label = 4;
                    case 4: return [2 /*return*/, this.strategy];
                    case 5:
                        e_1 = _a.sent();
                        throw new Error("Unexpected error while determining the request implementation: " + e_1);
                    case 6: return [2 /*return*/];
                }
            });
        }); };
        this.requiresHttp = function () {
            return (_this._options.connectMethod === 'http' || !_this.supportsExtensions() || Utils.checkVersionException());
        };
        this.setHttpStrategy = function () {
            Logger.debug('Using http strategy');
            _this.strategy = _this.getHttpStrategy();
        };
        this.supportsExtensions = function () {
            return (_this.supportsNativeHost() || _this.supportsSafariAppExt());
        };
        this.supportsHttp = function () {
            if (Utils.getXMLHttpRequest() === null) {
                return false;
            }
            return true;
        };
        this.supportsNativeHost = function () {
            return (browser_1.default.CHROME || browser_1.default.EDGE_WITH_EXTENSION || browser_1.default.FIREFOX);
        };
        this.supportsNpapi = function () {
            return (browser_1.default.IE || browser_1.default.SAFARI && !browser_1.default.SAFARI_NO_NPAPI);
        };
        this.supportsSafariAppExt = function () {
            return browser_1.default.SAFARI_NO_NPAPI;
        };
    }
    return Provider;
}());
exports.default = Provider;


/***/ }),

/***/ "./src/utils.ts":
/*!**********************!*\
  !*** ./src/utils.ts ***!
  \**********************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

/**
 * @desc Contains helper functions for the developer.
 *
 * @module Utils
 * @property {Object} BROWSER Contains the type of browser that we are currently
 *   on (based on user agent).
 *
 *   Format:
 *   ```
 *   {
 *     "OPERA": false,
 *     "IE": false,
 *     "CHROME": true,
 *     "FIREFOX": false,
 *     "FIREFOX_LEGACY": false,
 *     "EDGE_WITH_EXTENSION": false,
 *     "EDGE_LEGACY": false,
 *     "SAFARI": false,
 *     "SAFARI_NO_NPAPI": false
 *   }
 *   ```
 */
Object.defineProperty(exports, "__esModule", { value: true });
var tslib_1 = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
var Logger = tslib_1.__importStar(__webpack_require__(/*! ./logger */ "./src/logger.ts"));
var browser_1 = tslib_1.__importDefault(__webpack_require__(/*! ./helpers/browser */ "./src/helpers/browser.ts"));
exports.BROWSER = browser_1.default;
var constants_1 = __webpack_require__(/*! ./constants */ "./src/constants/index.ts");
var globals_1 = __webpack_require__(/*! ./helpers/globals */ "./src/helpers/globals.ts");
globals_1.ConnectGlobals.sessionId = generateUuid();
globals_1.ConnectGlobals.sessionKey = generateRandomStr(32);
var nextObjId = 0;
/**
 * Returns fasp initialize protocol
 */
function getInitUrl() {
    return 'fasp://initialize';
}
exports.getInitUrl = getInitUrl;
function getXMLHttpRequest() {
    if (typeof XMLHttpRequest === 'undefined') {
        // @ts-ignore
        XMLHttpRequest = function () {
            try {
                return new ActiveXObject('Msxml2.XMLHTTP.6.0');
            }
            catch (e) { }
            try {
                return new ActiveXObject('Msxml2.XMLHTTP.3.0');
            }
            catch (e) { }
            try {
                return new ActiveXObject('Microsoft.XMLHTTP');
            }
            catch (e) { }
            // This browser does not support XMLHttpRequest
            return;
        };
    }
    return new XMLHttpRequest();
}
exports.getXMLHttpRequest = getXMLHttpRequest;
////////////////////////////////////////////////////////////////////////////
// Compatibility functions
////////////////////////////////////////////////////////////////////////////
/**
 * Returns standardized error object
 */
function createError(errorCode, message) {
    var internalMessage = '';
    if (errorCode === -1) {
        internalMessage = 'Invalid request';
    }
    return { error: { code: errorCode, internal_message: internalMessage, user_message: message } };
}
exports.createError = createError;
/**
 * - str
 */
function parseJson(str) {
    var obj;
    if (typeof str === 'string' && (str.length === 0 || str.replace(/\s/g, '') === '{}')) {
        // return {};
    }
    try {
        obj = JSON.parse(str);
    }
    catch (e) {
        obj = createError(-1, e);
    }
    return obj;
}
exports.parseJson = parseJson;
////////////////////////////////////////////////////////////////////////////
// Helper Functions
////////////////////////////////////////////////////////////////////////////
function copyObject(obj) {
    var localObj = {};
    if (!isNullOrUndefinedOrEmpty(obj)) {
        for (var property in obj) {
            if (obj.hasOwnProperty(property)) {
                localObj[property] = obj[property];
            }
        }
    }
    return localObj;
}
exports.copyObject = copyObject;
/**
 * Checks if variable is null or undefined or empty.
 */
function isNullOrUndefinedOrEmpty(x) {
    return x === '' || x === null || typeof x === 'undefined';
}
exports.isNullOrUndefinedOrEmpty = isNullOrUndefinedOrEmpty;
/**
 * AW4.Utils.versionLessThan(version1, version2) -> bool
 *  - version1 (Number):  a version Integer
 *  - version2 (Number):  a version Integer
 *
 * Compares two version strings.
 * Returns true if version string 'a' is less than version string 'b'
 *     '1.2.1' < '1.11.3'
 *     '1.1'   < '2.1'
 *     '1'     = '1'
 *     '1.2'   < '2'
 * Note the following behavior:
 *     '1'     = '1.2'
 *     '1.2'   = '1'
 *  This helps with upgrade checks.  If at least version '4' is required, and
 *   '4.4.2' is installed, versionLessThan('4.4.2','4') will return false.
 *
 * If the version number contains a character that is not a numeral it ignores
 * it
 */
function versionLessThan(a, b) {
    var versionToArray = function (version) {
        var splits = version.split('.');
        var versionArray = new Array();
        for (var i_1 = 0; i_1 < splits.length; i_1++) {
            var versionPart = parseInt(splits[i_1], 10);
            if (!isNaN(versionPart)) {
                versionArray.push(versionPart);
            }
        }
        return versionArray;
    };
    var aArr = versionToArray(a);
    var bArr = versionToArray(b);
    var i;
    for (i = 0; i < Math.min(aArr.length, bArr.length); i++) {
        // if i=2, a=[0,0,1,0] and b=[0,0,2,0]
        if (aArr[i] < bArr[i]) {
            return true;
        }
        // if i=2, a=[0,0,2,0] and b=[0,0,1,0]
        if (aArr[i] > bArr[i]) {
            return false;
        }
        // a[i] and b[i] exist and are equal:
        // move on to the next version number
    }
    // all numbers equal (or all are equal and we reached the end of a or b)
    return false;
}
exports.versionLessThan = versionLessThan;
/**
 * Checks if user has previously chosen to continue with current version.
 */
function checkVersionException() {
    if (typeof (localStorage) === 'undefined') {
        return false;
    }
    var prevContinuedSeconds = localStorage.getItem(constants_1.LS_CONTINUED_KEY);
    if (prevContinuedSeconds !== undefined && prevContinuedSeconds !== null) {
        var currentTimeSeconds = Math.round(new Date().getTime() / 1000);
        if ((currentTimeSeconds - Number(prevContinuedSeconds)) < 60 * 24) {
            Logger.debug('User opted out of update');
            return true;
        }
    }
    return false;
}
exports.checkVersionException = checkVersionException;
function addVersionException() {
    if (typeof (localStorage) === 'undefined') {
        return;
    }
    localStorage.setItem(constants_1.LS_CONTINUED_KEY, String(Math.round(new Date().getTime() / 1000)));
}
exports.addVersionException = addVersionException;
/**
 * Helper function to generate deferred promise
 */
function generatePromiseData() {
    var resolver;
    var rejecter;
    var promise = new Promise(function (resolve, reject) {
        resolver = resolve;
        rejecter = reject;
    });
    return {
        promise: promise,
        resolver: resolver,
        rejecter: rejecter
    };
}
exports.generatePromiseData = generatePromiseData;
function generateUuid() {
    var date = new Date().getTime();
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        var r;
        // @ts-ignore
        r = ((date + 16) * Math.random()).toFixed() % 16;
        if (c !== 'x') {
            /*jslint bitwise: true */
            r = r & 0x3 | 0x8;
            /*jslint bitwise: false */
        }
        return r.toString(16);
    });
}
exports.generateUuid = generateUuid;
function generateRandomStr(size) {
    var text = '';
    var possible = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    for (var i = 0; i < size; i++) {
        text += possible.charAt(Math.floor(Math.random() * possible.length));
    }
    return text;
}
exports.generateRandomStr = generateRandomStr;
/**
 * Attempt to launch Connect. It will handle different browser
 * implementations to not end in an error page or launch multiple
 * times.
 *
 * @function
 * @static
 * @name launchConnect
 * @param {Callbacks} callbacks `success` and `error` functions to receive results.
 *
 * Result returned to success callback:
 * * `true` - If Connect is installed.
 * * `false` - If Connect is either not installed or we could not detect it.
 * @return {null}
 */
function launchConnect(userCallback) {
    var isRegistered = false;
    var callback = function (installed) {
        if (typeof userCallback === 'function') {
            userCallback(installed);
        }
    };
    var launchUri = getInitUrl();
    Logger.debug('Starting Connect session: ' + launchUri);
    if (browser_1.default.CHROME || browser_1.default.OPERA) {
        document.body.focus();
        document.body.onblur = function () {
            isRegistered = true;
        };
        // will trigger onblur
        document.location.href = launchUri;
        // Note: timeout could lety as per the browser version, have a higher value
        setTimeout(function () {
            // tslint:disable-next-line
            document.body.onblur = null;
            callback(isRegistered);
        }, 500);
    }
    else if (browser_1.default.EDGE_LEGACY || browser_1.default.EDGE_WITH_EXTENSION) {
        document.location.href = launchUri;
    }
    else if (browser_1.default.FIREFOX_LEGACY || browser_1.default.FIREFOX || browser_1.default.SAFARI_NO_NPAPI) {
        var dummyIframe = document.createElement('IFRAME');
        dummyIframe.src = launchUri;
        // Don't show the iframe and don't allow it to take up space
        dummyIframe.style.visibility = 'hidden';
        dummyIframe.style.position = 'absolute';
        dummyIframe.style.width = '0px';
        dummyIframe.style.height = '0px';
        dummyIframe.style.border = '0px';
        document.body.appendChild(dummyIframe);
    }
    // ELSE is handled by the NPAPI plugin
    return;
}
exports.launchConnect = launchConnect;
/**
 * Returns full URL from relative URL
 *
 * @function
 * @static
 * @name getFullURI
 *
 * @param {String} relativeURL The relative URL that we want the full path to. It
 *   must be relative to the current page being rendered. If a full URL is
 *   provided, it will return the same.
 * @return {String}
 * @example
 * // If current rendered page is https://example.com/my/page
 * let relativeURL = 'foo.txt'
 * AW4.Utils.getFullURI(relativeURL) // returns "https://example.com/my/page/foo.txt"
 */
function getFullURI(relativeURL) {
    if (typeof relativeURL !== 'string') {
        return;
    }
    var url = relativeURL;
    var a = document.createElement('a');
    a.href = url;
    var fullURL = a.href;
    if (fullURL.indexOf('/', fullURL.length - 1) !== -1) {
        fullURL = fullURL.slice(0, -1);
    }
    return fullURL;
}
exports.getFullURI = getFullURI;
/**
 * Output base64 string from utf8 or unicode string
 *
 * @function
 * @static
 * @name utoa
 *
 * @param {String} inputString utf8 or unicode string input.
 * @return {String}
 *
 * @example
 * let inputString = 'foo'
 * AW4.Utils.atou(inputString) // returns "Zm9v"
 */
function utoa(inputString) {
    if (window.btoa) {
        return window.btoa(unescape(encodeURIComponent(inputString)));
    }
    else {
        return inputString;
    }
}
exports.utoa = utoa;
/**
 * Output unicode string from base64 string
 *
 * @function
 * @static
 * @name atou
 *
 * @param {String} inputString base64 string input.
 * @return {String}
 *
 * @example
 * let inputString = 'Zm9v'
 * AW4.Utils.atou(inputString) // returns "foo"
 */
function atou(inputString) {
    if (window.atob) {
        return decodeURIComponent(escape(window.atob(inputString)));
    }
    else {
        return inputString;
    }
}
exports.atou = atou;
function nextObjectId() {
    // Return an incrementing id even if file was reloaded
    nextObjId++;
    return nextObjId;
}
exports.nextObjectId = nextObjectId;
/** Returns true if status code is 2xx */
function isSuccessCode(code) {
    return code >= 200 && code < 300;
}
exports.isSuccessCode = isSuccessCode;
function getLocalStorage(key) {
    try {
        if (typeof (localStorage) === 'undefined') {
            return '';
        }
        return localStorage.getItem(key);
    }
    catch (error) {
        // Accessing local storage can be blocked by third party cookie settings
        Logger.error('Error accessing localStorage: ', JSON.stringify(error));
        return '';
    }
}
exports.getLocalStorage = getLocalStorage;
function recordConnectDetected() {
    window.localStorage.setItem(constants_1.LS_CONNECT_DETECTED, Date.now().toString());
}
exports.recordConnectDetected = recordConnectDetected;
function setLocalStorage(key, value) {
    try {
        if (typeof (localStorage) === 'undefined') {
            return '';
        }
        return localStorage.setItem(key, value);
    }
    catch (error) {
        // Accessing local storage can be blocked by third party cookie settings
        Logger.error('Error accessing localStorage: ', JSON.stringify(error));
        return;
    }
}
exports.setLocalStorage = setLocalStorage;
function entropyOk(id) {
    var entropy = 0;
    var len = id.length;
    var charFreq = Object.create({});
    id.split('').forEach(function (s) {
        if (charFreq[s]) {
            charFreq[s] += 1;
        }
        else {
            charFreq[s] = 1;
        }
    });
    for (var s in charFreq) {
        var percent = charFreq[s] / len;
        entropy -= percent * (Math.log(percent) / Math.log(2));
    }
    return entropy > 3.80;
}
exports.entropyOk = entropyOk;
function isError(x) {
    return (x && x.error !== undefined);
}
exports.isError = isError;


/***/ }),

/***/ "./src/version.ts":
/*!************************!*\
  !*** ./src/version.ts ***!
  \************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
exports.__VERSION__ = '3.10.0';


/***/ })

/******/ });
//# sourceMappingURL=asperaweb-4.js.map