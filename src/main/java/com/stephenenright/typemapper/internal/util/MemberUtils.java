package com.stephenenright.typemapper.internal.util;

import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

import com.stephenenright.typemapper.TypeAccessLevel;

public abstract class MemberUtils {

    private MemberUtils() {

    }

    public static boolean isMemberAccessAllowed(Member member, TypeAccessLevel accessLevel) {
        if (accessLevel == null) {
            return false;
        }

        final int modifier = member.getModifiers();

        if (accessLevel == TypeAccessLevel.PUBLIC) {
            return Modifier.isPublic(modifier);
        } else if (accessLevel == TypeAccessLevel.PROTECTED) {
            return Modifier.isPublic(modifier) || Modifier.isProtected(modifier);
        } else if (accessLevel == TypeAccessLevel.PACKAGE_PRIVATE) {
            return Modifier.isPublic(modifier) || Modifier.isProtected(modifier) || !Modifier.isPrivate(modifier);
        } else {
            return true;
        }
    }
}
