package uk.ac.ebi.biostudies.file.download;

import au.com.bytecode.opencsv.CSVReader;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FilteredMageTabDownloadStream {
    public final static String IDF_FILE_NAME_PATTERN = "^.+[.]idf[.]txt$";
    public final static String SDRF_FILE_NAME_PATTERN = "^.+[.]sdrf[.]txt$";
    private static final char DEFAULT_COL_DELIMITER = 0x9;
    private static final char DEFAULT_COL_QUOTE_CHAR = '"';
    private static final String DEFAULT_CHARSET = "UTF-8";
    private final byte[] filteredFile;
    private final InputStream fileContent;

    public FilteredMageTabDownloadStream(String fileName, InputStream fileContent) throws IOException {
        if (null == fileContent || fileName == null) {
            throw new IllegalArgumentException("File cannot be null");
        }
        this.fileContent = fileContent;
        if (fileName.matches(IDF_FILE_NAME_PATTERN)) {
            filteredFile = new FilteredMageTabDownloadStream.IdfFilter(fileContent).getFilteredFile();
        } else if (fileName.matches(SDRF_FILE_NAME_PATTERN)) {
            filteredFile = new FilteredMageTabDownloadStream.SdrfFilter(fileContent).getFilteredFile();
        } else {
            filteredFile = null;
        }
    }

    public boolean isSupported() {
        return filteredFile != null;
    }

    public InputStream getInputStream() {
        if (null != filteredFile) {
            return new ByteArrayInputStream(filteredFile);
        }
        return fileContent;
    }

    static abstract class MageTabFilter {
        public static String processHeader(String header) {
            if (header == null) {
                return "";
            } else {
                String main = "";
                String type = "";
                String subtype = "";

                // reduce header to only text, excluding types and subtype
                main = header;

                // remove subtype first
                if (header.contains("(")) {
                    // the main part is everything up to ( - there shouldn't be cases of this?
                    main = header.substring(0, header.indexOf('('));
                    // the qualifier is everything after (
                    subtype = "(" + extractSubtype(header) + ")";
                }
                // remove type second
                if (header.contains("[")) {
                    // the main part is everything up to [
                    main = header.substring(0, header.indexOf('['));
                    // the qualifier is everything after [
                    type = "[" + extractType(header) + "]";
                }

                StringBuilder processed = new StringBuilder();

                for (int i = 0; i < main.length(); i++) {
                    char c = main.charAt(i);
                    switch (c) {
                        case ' ':
                        case '\t':
                            continue;
                        default:
                            processed.append(Character.toLowerCase(c));
                    }
                }

                // add any [] (type) or () (subtype) qualifiers
                processed.append(type).append(subtype);

                return processed.toString();
            }
        }

        public static String extractType(String header) {
            return header.contains("[") ? header.substring(header.indexOf("[") + 1, header.lastIndexOf("]")) : "";
        }

        public static String extractSubtype(String header) {
            // remove typing first
            String untypedHeader = (header.contains("[")
                    ? header.replace(header.substring(header.indexOf("[") + 1, header.lastIndexOf("]")), "")
                    : header);
            // now check untypedHeader for parentheses
            return untypedHeader.contains("(") ?
                    untypedHeader.substring(untypedHeader.indexOf("(") + 1, untypedHeader.lastIndexOf(")")) : "";
        }
    }

    static class IdfFilter extends FilteredMageTabDownloadStream.MageTabFilter {
        private final static String IDF_FILTER_PATTERN = "^(person.+|pubmedid|publication.+|comment\\[AEAnonymousReview\\])$";
        InputStream content;

        public IdfFilter(InputStream content) {
            this.content = content;
        }

        public byte[] getFilteredFile() throws IOException {
            StringBuilder sb = new StringBuilder();

            try (BufferedReader br = new BufferedReader(new InputStreamReader(content, DEFAULT_CHARSET))) {
                for (String line; (line = br.readLine()) != null; ) {
                    String header = processHeader(line.replaceFirst("^([^\t]*).*$", "$1"));
                    if (!header.matches(IDF_FILTER_PATTERN)) {
                        sb.append(line).append(System.getProperty("line.separator"));
                    }
                }
            }

            return sb.toString().getBytes(DEFAULT_CHARSET);
        }
    }

    static class SdrfFilter extends FilteredMageTabDownloadStream.MageTabFilter {
        private final static String SDRF_FILTER_PATTERN = "^(performer|provider)$";
        private final InputStream content;

        public SdrfFilter(InputStream content) {
            this.content = content;
        }

        public byte[] getFilteredFile() throws IOException {
            StringBuilder sb = new StringBuilder();

            try (BufferedReader br = new BufferedReader(new InputStreamReader(content, DEFAULT_CHARSET))) {
                CSVReader ff = new CSVReader(br, DEFAULT_COL_DELIMITER, DEFAULT_COL_QUOTE_CHAR);
                List<String[]> table = ff.readAll();
                if (null != table && table.size() > 0) {
                    Set<Integer> columnsToOmit = new HashSet<>();
                    String[] headers = table.get(0);
                    for (int col = 0; null != headers && col < headers.length; col++) {
                        String header = processHeader(headers[col]);
                        if (header.matches(SDRF_FILTER_PATTERN)) {
                            columnsToOmit.add(col);
                        } else if (col > 0 && columnsToOmit.contains(col - 1) && header.startsWith("comment[")) {
                            columnsToOmit.add(col);
                        }
                    }
                    outputLine(sb, headers, columnsToOmit);
                    for (int line = 1; line < table.size(); line++) {
                        outputLine(sb, table.get(line), columnsToOmit);
                    }
                }
            }
            return sb.toString().getBytes(DEFAULT_CHARSET);
        }

        private void outputLine(StringBuilder sb, String[] line, Set<Integer> columnsToOmit) {
            boolean shouldAddDelimiter = false;

            for (int col = 0; null != line && col < line.length; col++) {
                if (!columnsToOmit.contains(col)) {
                    if (shouldAddDelimiter) {
                        sb.append(DEFAULT_COL_DELIMITER);
                    }
                    sb.append(line[col]);
                    shouldAddDelimiter = true;
                }
            }
            if (null != line) {
                sb.append(System.getProperty("line.separator"));
            }
        }
    }

}
