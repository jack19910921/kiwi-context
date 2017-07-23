package org.kiwi.context;

import java.util.Collection;
import java.util.Map;

/**
 * @email jack.liu.19910921@gmail.com
 * Created by jack on 17/7/23.
 */
public class Utils {

    public static boolean containsAny(CharSequence cs, CharSequence... searchCharSequences) {
        if (isEmpty(cs) || isEmpty(searchCharSequences)) {
            return false;
        }
        for (CharSequence searchCharSequence : searchCharSequences) {
            if (contains(cs, searchCharSequence)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAnyBlank(final CharSequence... css) {
        if (isEmpty(css)) {
            return true;
        }
        for (final CharSequence cs : css) {
            if (isBlank(cs)) {
                return true;
            }
        }
        return false;
    }

    public static boolean equals(final CharSequence cs1, final CharSequence cs2) {
        if (cs1 == cs2) {
            return true;
        }
        if (cs1 == null || cs2 == null) {
            return false;
        }
        if (cs1 instanceof String && cs2 instanceof String) {
            return cs1.equals(cs2);
        }
        return regionMatches(cs1, false, 0, cs2, 0, Math.max(cs1.length(), cs2.length()));
    }

    public static boolean equalsIgnoreCase(final CharSequence str1, final CharSequence str2) {
        if (str1 == null || str2 == null) {
            return str1 == str2;
        } else if (str1 == str2) {
            return true;
        } else if (str1.length() != str2.length()) {
            return false;
        } else {
            return regionMatches(str1, true, 0, str2, 0, str1.length());
        }
    }

    private static boolean regionMatches(final CharSequence cs, final boolean ignoreCase, final int thisStart,
                                         final CharSequence substring, final int start, final int length) {
        if (cs instanceof String && substring instanceof String) {
            return ((String) cs).regionMatches(ignoreCase, thisStart, (String) substring, start, length);
        }
        int index1 = thisStart;
        int index2 = start;
        int tmpLen = length;

        while (tmpLen-- > 0) {
            final char c1 = cs.charAt(index1++);
            final char c2 = substring.charAt(index2++);

            if (c1 == c2) {
                continue;
            }

            if (!ignoreCase) {
                return false;
            }

            // The same check as in String.regionMatches():
            if (Character.toUpperCase(c1) != Character.toUpperCase(c2)
                    && Character.toLowerCase(c1) != Character.toLowerCase(c2)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isEmpty(final Object[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(final Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }

    public static boolean isEmpty(final Map map) {
        return map == null || map.isEmpty();
    }

    public static <T> boolean isNotEmpty(final T[] array) {
        return (array != null && array.length != 0);
    }

    public static boolean contains(final CharSequence seq, final CharSequence searchSeq) {
        if (seq == null || searchSeq == null) {
            return false;
        }
        return indexOf(seq, searchSeq, 0) >= 0;
    }

    public static int indexOf(final CharSequence cs, final CharSequence searchChar, final int start) {
        return cs.toString().indexOf(searchChar.toString(), start);
    }

}
