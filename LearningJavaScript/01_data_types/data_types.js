var a;
//var b; 
var c = null;
console.log(typeof a);
console.log(typeof b);
//console.log(a == b);                                // ReferenceError: b is not defined 
console.log(a == c);

console.log("\n*******Infinity Test********");
var biggerThanMaxValue = Number.MAX_VALUE + Number.MAX_VALUE;
console.log(isFinite(biggerThanMaxValue));                                      //false 
console.log(biggerThanMaxValue == Number.POSITIVE_INFINITY);                    // true
console.log(biggerThanMaxValue);                                                //Infinity
var infinity = 10/0;
console.log(infinity);                                                          // 输出Infinity. 和书上说的不一样！
console.log(infinity == NaN);

console.log("\n***********NaN Test*********");
var nan = 0/0;
console.log(nan); 
console.log(NaN == NaN);                                                         // false! 
console.log(NaN * 10);                                                           // Any calculation on NaN is still NaN
console.log(isNaN(nan));

console.log("\n***********Number conversion for NaN*********");
console.log(isNaN(NaN));                                                         //true
console.log(isNaN(10));                                                          //false - 10 is a number
console.log(isNaN("10"));                                                        //false - can be converted to number 10
console.log(isNaN("blue"));                                                      //true - cannot be converted to a number
console.log(isNaN(true));                                                        //false - can be converted to number 1

console.log("\n***********parseInt*********");
console.log(parseInt("1234blue"));                                               //1234
console.log(parseInt(""));                                                       //NaN
console.log(parseInt("0xA"));                                                    //10 - hexadecimal
console.log(parseInt(22.5));                                                     //22
console.log(parseInt("70"));                                                     //70 - decimal
console.log(parseInt("0xf"));                                                    //15 - hexadecimal

console.log("\n***********String*********");
var text = "This is the letter sigma: \u03a3.";
console.log(text);
console.log(text.length);                                                        //outputs 28, because each char is 16b, so \u030a is one char.

console.log("\n*************Data type casting***************");
console.log("********boolean********");
console.log(Boolean(undefined));
console.log(Boolean(null));
console.log(Boolean(0));
console.log(Boolean(""));
console.log(Boolean(NaN));
console.log(Boolean(5));

console.log("********Number()********");                                         // null, "", "1234abcd" are different for Number() and parseInt()
console.log(Number(undefined));           
console.log(Number(null));                                                       //0
console.log(Number(false));                   
console.log(Number(true));
console.log(Number(""));                                                         //0
console.log(Number("1234"));
console.log(Number("1234abcd"));                                                 //NaN
console.log(Number("0xf"));

console.log("\n***********parseInt()*********");
console.log(parseInt(undefined));
console.log(parseInt(null));                                                     //NaN
console.log(parseInt("1234blue"));                                               //1234
console.log(parseInt(""));                                                       //NaN
console.log(parseInt("0xA"));                                                    
console.log(parseInt(22.5));                                                     //22
console.log(parseInt("70"));                                                     
console.log(parseInt("0xf"));                                                    



