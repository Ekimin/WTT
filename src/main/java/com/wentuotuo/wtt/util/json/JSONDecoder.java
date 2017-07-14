//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.util.json;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.lang.StringX;

public class JSONDecoder {
    public JSONDecoder() {
    }

    public static JSONObject decode(String var0) throws JSONException {
        return StringX.isSpace(var0)?null:decode(var0.toCharArray());
    }

    public static JSONObject decode(char[] var0) throws JSONException {
        return var0 != null && var0.length >= 2?decode(var0, 0, var0.length - 1):null;
    }

    public static JSONObject decode(char[] var0, int var1, int var2) throws JSONException {
        if(var0 != null && var2 - var1 >= 1) {
            JSONObject var3 = null;
            int[] var4 = trimSpace(var0, var1, var2);
            if(var0[var4[0]] == 123 && var0[var4[1]] == 125) {
                var3 = decodeObject(var0, var4[0], var4[1]);
            } else {
                if(var0[var4[0]] != 91 || var0[var4[1]] != 93) {
                    throw new JSONException("Invalid json string, start or end char is not object or array tag!");
                }

                var3 = decodeArray(var0, var4[0], var4[1]);
            }

            return var3;
        } else {
            return null;
        }
    }

    private static JSONObject decodeObject(char[] var0, int var1, int var2) throws JSONException {
        JSONObject var3 = JSONObject.createObject();
        int[] var4 = trimSpace(var0, var1 + 1, var2 - 1);
        if(var4[0] >= var4[1]) {
            return var3;
        } else {
            int var5 = var1 + 1;
            int var6 = var5;

            while(true) {
                while(var5 < var2) {
                    char var7 = var0[var5];
                    int var8;
                    if(var7 == 34) {
                        for(var8 = var5++; (var0[var5] != 34 || var0[var5] == 34 && var0[var5 - 1] == 92) && var5 < var2; ++var5) {
                            ;
                        }

                        if(var5 == var2) {
                            throw new JSONException("unclosed string @ " + var8);
                        }

                        ++var5;
                    } else if(var7 == 39) {
                        for(var8 = var5++; (var0[var5] != 39 || var0[var5] == 39 && var0[var5 - 1] == 92) && var5 < var2; ++var5) {
                            ;
                        }

                        if(var5 == var2) {
                            throw new JSONException("unclosed string @ " + var8);
                        }

                        ++var5;
                    } else {
                        int var9;
                        if(var7 == 123) {
                            var8 = 1;
                            var9 = var5++;

                            while(var5 < var2) {
                                if(var0[var5] == 123) {
                                    ++var8;
                                    ++var5;
                                } else {
                                    if(var0[var5] == 125) {
                                        --var8;
                                        if(var8 == 0) {
                                            break;
                                        }
                                    }

                                    ++var5;
                                }
                            }

                            if(var5 == var2) {
                                throw new JSONException("unclosed object @ " + var9);
                            }

                            ++var5;
                        } else if(var7 != 91) {
                            if(var7 == 44) {
                                JSONElement var10 = createObjectElement(var0, var6, var5 - 1);
                                var3.add(var10);
                                ++var5;
                                var6 = var5;
                            } else {
                                ++var5;
                            }
                        } else {
                            var8 = 1;
                            var9 = var5++;

                            while(var5 < var2) {
                                if(var0[var5] == 91) {
                                    ++var8;
                                    ++var5;
                                } else {
                                    if(var0[var5] == 93) {
                                        --var8;
                                        if(var8 == 0) {
                                            break;
                                        }
                                    }

                                    ++var5;
                                }
                            }

                            if(var5 == var2) {
                                throw new JSONException("unclosed array @ " + var9);
                            }

                            ++var5;
                        }
                    }
                }

                var3.add(createObjectElement(var0, var6, var5 - 1));
                return var3;
            }
        }
    }

    private static JSONObject decodeArray(char[] var0, int var1, int var2) throws JSONException {
        JSONObject var3 = JSONObject.createArray();
        int[] var4 = trimSpace(var0, var1 + 1, var2 - 1);
        if(var4[0] > var4[1]) {
            return var3;
        } else {
            int var5 = var1 + 1;
            int var6 = var5;

            while(true) {
                while(var5 < var2) {
                    char var7 = var0[var5];
                    int var8;
                    if(var7 == 34) {
                        for(var8 = var5++; (var0[var5] != 34 || var0[var5] == 34 && var0[var5 - 1] == 92) && var5 < var2; ++var5) {
                            ;
                        }

                        if(var5 == var2) {
                            throw new JSONException("unclosed string @ " + var8);
                        }

                        ++var5;
                    } else if(var7 == 39) {
                        for(var8 = var5++; (var0[var5] != 39 || var0[var5] == 39 && var0[var5 - 1] == 92) && var5 < var2; ++var5) {
                            ;
                        }

                        if(var5 == var2) {
                            throw new JSONException("unclosed string @ " + var8);
                        }

                        ++var5;
                    } else {
                        int var9;
                        if(var7 == 123) {
                            var8 = 1;
                            var9 = var5++;

                            while(var5 < var2) {
                                if(var0[var5] == 123) {
                                    ++var8;
                                    ++var5;
                                } else {
                                    if(var0[var5] == 125) {
                                        --var8;
                                        if(var8 == 0) {
                                            break;
                                        }
                                    }

                                    ++var5;
                                }
                            }

                            if(var5 == var2) {
                                throw new JSONException("unclosed object @ " + var9);
                            }

                            ++var5;
                        } else if(var7 != 91) {
                            if(var7 == 44) {
                                JSONElement var10 = createElementNoName(var0, var6, var5 - 1);
                                var3.add(var10);
                                ++var5;
                                var6 = var5;
                            } else {
                                ++var5;
                            }
                        } else {
                            var8 = 1;
                            var9 = var5++;

                            while(var5 < var2) {
                                if(var0[var5] == 91) {
                                    ++var8;
                                    ++var5;
                                } else {
                                    if(var0[var5] == 93) {
                                        --var8;
                                        if(var8 == 0) {
                                            break;
                                        }
                                    }

                                    ++var5;
                                }
                            }

                            if(var5 == var2) {
                                throw new JSONException("unclosed array @ " + var9);
                            }

                            ++var5;
                        }
                    }
                }

                var3.add(createElementNoName(var0, var6, var5 - 1));
                return var3;
            }
        }
    }

    private static JSONElement createObjectElement(char[] var0, int var1, int var2) throws JSONException {
        int[] var3 = trimSpace(var0, var1, var2);
        if(var3[0] >= var3[1]) {
            return JSONElement.createNull();
        } else {
            int var4 = var3[0];

            while(var4 <= var3[1]) {
                if(var0[var4] == 34) {
                    ++var4;

                    while((var0[var4] != 34 || var0[var4] == 34 && var0[var4 - 1] == 92) && var4 <= var3[1]) {
                        ++var4;
                    }

                    if(var4 == var3[1]) {
                        throw new JSONException("unclosed string @ " + var3[0]);
                    }

                    ++var4;
                } else if(var0[var4] != 39) {
                    if(var0[var4] == 58) {
                        break;
                    }

                    ++var4;
                } else {
                    ++var4;

                    while(var0[var4] != 39 && var4 <= var3[1]) {
                        ++var4;
                    }

                    if(var4 == var3[1]) {
                        throw new JSONException("unclosed string @ " + var3[0]);
                    }

                    ++var4;
                }
            }

            if(var4 > var3[1]) {
                throw new JSONException("object element missing ':' @ " + var3[1]);
            } else {
                String var5 = createElementName(var0, var3[0], var4 - 1);
                JSONElement var6 = createElementNoName(var0, var4 + 1, var3[1]);
                if(var5 != null) {
                    var6.setName(var5);
                }

                return var6;
            }
        }
    }

    private static String createElementName(char[] var0, int var1, int var2) throws JSONException {
        String var3 = null;
        int[] var4 = trimSpace(var0, var1, var2);
        if(var4[0] >= var4[1]) {
            return null;
        } else {
            int var5;
            if(var0[var4[0]] == 34) {
                for(var5 = var4[0] + 1; (var0[var5] != 34 || var0[var5] == 34 && var0[var5 - 1] == 92) && var5 <= var4[1]; ++var5) {
                    ;
                }

                if(var5 != var4[1]) {
                    throw new JSONException(var5 + 1, "space or  ':'", var0[var5 + 1]);
                }

                var3 = escape(var0, var4[0] + 1, var4[1] - var4[0] - 1);
            } else if(var0[var4[0]] == 39) {
                for(var5 = var4[0] + 1; var0[var5] != 39 && var5 <= var4[1]; ++var5) {
                    ;
                }

                if(var5 != var4[1]) {
                    throw new JSONException(var5 + 1, "space or  ':'", var0[var5 + 1]);
                }

                var3 = escape(var0, var4[0] + 1, var4[1] - var4[0] - 1);
            } else {
                var3 = escape(var0, var4[0], var4[1] - var4[0] + 1);
            }

            return var3;
        }
    }

    private static JSONElement createElementNoName(char[] var0, int var1, int var2) throws JSONException {
        int[] var3 = trimSpace(var0, var1, var2);
        if(var3[0] > var3[1]) {
            return JSONElement.createNull();
        } else {
            JSONElement var4 = null;
            int var5;
            if(var0[var3[0]] == 34) {
                for(var5 = var3[0] + 1; (var0[var5] != 34 || var0[var5] == 34 && var0[var5 - 1] == 92) && var5 <= var3[1]; ++var5) {
                    ;
                }

                if(var5 != var3[1]) {
                    throw new JSONException(var5 + 1, "space or  ','", var0[var5 + 1]);
                }

                var4 = JSONElement.valueOf(escape(var0, var3[0] + 1, var3[1] - var3[0] - 1));
            } else if(var0[var3[0]] == 39) {
                for(var5 = var3[0] + 1; (var0[var5] != 39 || var0[var5] == 39 && var0[var5 - 1] == 92) && var5 <= var3[1]; ++var5) {
                    ;
                }

                if(var5 != var3[1]) {
                    throw new JSONException(var5 + 1, "space or  ','", var0[var5 + 1]);
                }

                var4 = JSONElement.valueOf(escape(var0, var3[0] + 1, var3[1] - var3[0] - 1));
            } else if(var0[var3[0]] == 123) {
                if(var0[var3[1]] != 125) {
                    throw new JSONException(var0[var3[1]] + 1, "} ','", var0[var0[var3[1] + 1]]);
                }

                var4 = new JSONElement((byte)4);
                var4.setValue(decode(var0, var3[0], var3[1]));
            } else if(var0[var3[0]] == 91) {
                if(var0[var3[1]] != 93) {
                    throw new JSONException(var0[var3[1]] + 1, "] ','", var0[var0[var3[1] + 1]]);
                }

                var4 = new JSONElement((byte)5);
                var4.setValue(decode(var0, var3[0], var3[1]));
            } else {
                String var8 = new String(var0, var3[0], var3[1] - var3[0] + 1);
                if(var8.equalsIgnoreCase("null")) {
                    var4 = JSONElement.createNull();
                } else if(var8.equalsIgnoreCase("true")) {
                    var4 = JSONElement.valueOf(true);
                } else if(var8.equalsIgnoreCase("false")) {
                    var4 = JSONElement.valueOf(false);
                } else {
                    try {
                        if(var8.indexOf(46) > 0) {
                            var4 = JSONElement.valueOf(Double.parseDouble(var8));
                        } else {
                            var4 = JSONElement.valueOf(Integer.parseInt(var8));
                        }
                    } catch (Exception var7) {
                        var4 = JSONElement.valueOf(0.0D / 0.0);
                    }
                }
            }

            return var4;
        }
    }

    private static int[] trimSpace(char[] var0, int var1, int var2) {
        while(Character.isWhitespace(var0[var1]) && var1 < var0.length) {
            ++var1;
        }

        while(Character.isWhitespace(var0[var2]) && var2 >= 0) {
            --var2;
        }

        return new int[]{var1, var2};
    }

    private static String escape(char[] var0, int var1, int var2) throws JSONException {
        if(var2 < 2) {
            if(var0[var1] == 92) {
                throw new JSONException("Invalid escape sequnce @ " + var1);
            } else {
                return new String(var0, var1, var2);
            }
        } else {
            StringBuffer var3 = new StringBuffer(var2);
            int var4 = var1;
            int var5 = var1 + var2;

            while(var4 < var5 - 1) {
                if(var0[var4] != 92) {
                    var3.append(var0[var4]);
                    ++var4;
                } else if(var0[var4 + 1] == 117) {
                    if(var5 - var4 < 6) {
                        throw new JSONException("Invalid escape sequnce @ " + var4);
                    }

                    try {
                        int var6 = Integer.parseInt(new String(var0, var4 + 2, 4), 16);
                        var3.append((char)var6);
                        var4 += 6;
                    } catch (NumberFormatException var7) {
                        WTT.getLog().debug("Number format error", var7);
                        throw new JSONException("Invalid escape sequnce @ " + var4);
                    }
                } else {
                    switch(var0[var4 + 1]) {
                        case '"':
                            var3.append("\"");
                            break;
                        case '\'':
                            var3.append("'");
                            break;
                        case '/':
                            var3.append("/");
                            break;
                        case '\\':
                            var3.append("\\");
                            break;
                        case 'b':
                            var3.append("\b");
                            break;
                        case 'f':
                            var3.append("\f");
                            break;
                        case 'n':
                            var3.append("\n");
                            break;
                        case 'r':
                            var3.append("\r");
                            break;
                        case 't':
                            var3.append("\t");
                            break;
                        default:
                            throw new JSONException("Invalid escape sequnce @ " + var4);
                    }

                    var4 += 2;
                }
            }

            if(var4 < var1 + var2) {
                var3.append(var0[var4]);
            }

            return var3.toString();
        }
    }
}
