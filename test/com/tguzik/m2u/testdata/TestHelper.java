package com.tguzik.m2u.testdata;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Ignore;

import com.google.common.base.CharMatcher;

@Ignore
public class TestHelper
{
    public static InputStream getFileInPackage( String prefix, Class<?> clazzWithinPackage, String fileName ) throws FileNotFoundException {
        return getFileInPackage(prefix,
                                clazzWithinPackage.getPackage().getName().replaceAll("\\.", "/"),
                                fileName);
    }

    public static InputStream getFileInPackage( String prefix, String path, String fileName ) throws FileNotFoundException {
        return new FileInputStream(String.format("%s%s/%s", prefix, path, fileName));
    }

    public static String fileToString( String prefix, Class<?> clazzWithinPackage, String fileName ) throws IOException {
        return IOUtils.toString(getFileInPackage(prefix, clazzWithinPackage, fileName));
    }

    public static String fileToString( String prefix, String path, String fileName ) throws IOException {
        return IOUtils.toString(getFileInPackage(prefix, path, fileName));
    }

    public static String removeCharacterFeed( String in ) {
        return CharMatcher.anyOf("\r").removeFrom(in);
    }
}
