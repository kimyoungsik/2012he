package view;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import AbbrRepository.Word;

public class RepoTableModel extends AbstractTableModel {

    /**
     * Generated serial version ID for RepoTableModel class
     */
    private static final long serialVersionUID = -2666386620285658611L;
    private boolean DEBUG = false;
    public static final int ORIG_INDEX = 0;
    public static final int ABBR_INDEX = 1;
    public static final int CONVERTER_INDEX = 2;
    //private static final String[] columnNames = {"Original Text", "Abbreviation", "Converter"};
    private static final String[] columnNames = {"단어", "축약어", "변환자"};
    private List<Word> words;

    public RepoTableModel() {
        words = new ArrayList<Word>();
    }

    public RepoTableModel(List<Word> words) {
        this.words = words;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public int getRowCount() {
        return words.size();
    }

    @Override
    public Object getValueAt(int row, int col) {
        switch (col) {
            case ORIG_INDEX: {
                return words.get(row).getOrig();
            }
            case ABBR_INDEX: {
                return words.get(row).getAbbr();
            }
            case CONVERTER_INDEX: {
                return words.get(row).getConverter();
            }
            default:
                return null;
        }
    }

    public List<Word> getWords() {
        return words;
    }

    public boolean isCellEditable(int row, int col) {
        return true;
    }

    public void setValueAt(Object value, int row, int col) {
        if (DEBUG) {
            System.out.println("Setting value at " + row + "," + col
                    + " to " + value
                    + " (an instance of "
                    + value.getClass() + ")");
        }
        switch (col) {
            case ORIG_INDEX: {
                words.get(row).setOrig((String) value);
                return;
            }
            case ABBR_INDEX: {
                words.get(row).setAbbr((String) value);
                return;
            }
            case CONVERTER_INDEX: {
                words.get(row).setConverter((String) value);
                return;
            }
        }
        fireTableCellUpdated(row, col);

        if (DEBUG) {
            System.out.println("New value of data:");
        }
    }

    public void addRow(String orig, String abbr, String converter) {
        Word newword = new Word(orig, abbr, converter);
        words.add(newword);
        fireTableRowsInserted(words.size() - 1, words.size() - 1);
    }

    public void removeRow(int row) {
        words.remove(row);
        fireTableRowsDeleted(row, row);
    }

    public String getOrigForAbbr(String conv, String abbr) {
        String orig = null;

        //앞에서부터 전부 다 하려면 반복이 많아짐
        // 뒤에서부터 찾고 찾는데로 끝나게 수정
        //2011.10.24 김영식
        //최근에 수정한것을 바꿔주기위해서 뒤에서 부터 찾음

        /*for (int i = 0; i < words.size(); i++){
        Word word = words.get(i);
        if ((word.getConverter().equals(conv)) && (word.getAbbr().equals(abbr))){
        orig = word.getOrig();
        }
        }*/


        for (int i = words.size() - 1; i > -1; i--) {
            Word word = words.get(i);
            if ((word.getConverter().equals(conv)) && (word.getAbbr().equals(abbr))) {
                orig = word.getOrig();
                i = -1;
            }
        }
        return orig;
    }

    public void printWords() {
        List<Word> words2print = getWords();
        String output = new String();
        for (int i = 0; i < words2print.size(); i++) {
            Word word = words2print.get(i);
            //TODO: if the same words are saved to the same csv it appends the same word over and over!!

            output += word.getOrig() + ", " + word.getAbbr() + ", " + word.getConverter() + '\n';
        }
        System.out.println(output);
    }
}
