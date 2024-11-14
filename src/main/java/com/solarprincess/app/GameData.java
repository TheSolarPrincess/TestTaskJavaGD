package com.solarprincess.app;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GameData {
    private ArrayList<Symbol> symbols;
    private ArrayList<Reel> reels;

    class Symbol {
        public String name;
        public Integer payout1;
        public Integer payout2;
        public Integer payout3;
    }

    class Reel {
        public ArrayList<Integer> dist = new ArrayList<>();
    }

    public class ReelResult {
        public int[] symbols;
        public int payout;
        public Boolean ThreeOfAKind() {
            return symbols[0] == symbols[1] && symbols[1] == symbols[2];
        }
        public Boolean TwoOfAKind() {
            return symbols[0] == symbols[1] && symbols[1] != symbols[2];
        }
        public Boolean OneOfAKind() {
            return symbols[0] != symbols[1];
        }
    }

    public GameData(String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new HSSFWorkbook(fis);
        ProcessSheet(workbook.getSheetAt(0));
        workbook.close();
        fis.close();
    }

    private void ProcessSheet(Sheet sheet) {
        // Everything is hardcoded

        symbols = new ArrayList<>();
        for (int i = 3; i <= 13; i++) {
            Row r = sheet.getRow(i-1);
            Symbol p = new Symbol();
            p.name = r.getCell(1).getStringCellValue();
            p.payout3 = ((int)r.getCell(4).getNumericCellValue());
            p.payout2 = ((int)r.getCell(5).getNumericCellValue());
            p.payout1 = ((int)r.getCell(6).getNumericCellValue());
            symbols.add(p);
        }

        reels = new ArrayList<>();
        reels.add(new Reel()); reels.add(new Reel()); reels.add(new Reel()); 
        for (int i = 19; i <= 29; i++) {
            Row r = sheet.getRow(i-1);
            reels.get(0).dist.add(((int)r.getCell(1).getNumericCellValue()));
            reels.get(1).dist.add(((int)r.getCell(2).getNumericCellValue()));
            reels.get(2).dist.add(((int)r.getCell(3).getNumericCellValue()));
        }
    }

    private int Reel(int reel, int rand) {
        ArrayList<Integer> dist = reels.get(reel).dist;
        int symbolIndex = 0;
        int counter = rand;
        while (counter > 0) {
            counter -= dist.get(symbolIndex);
            symbolIndex += 1;
        }
        return symbolIndex;
    }

    public ReelResult Roll() {
        Random rand = new Random();
        int[] r = {
            Reel(0, rand.nextInt(25)+1),
            Reel(1, rand.nextInt(25)+1),
            Reel(2, rand.nextInt(25)+1)
        };
        ReelResult res = new ReelResult();
        res.symbols = r;
        //int res = 0;
        if (res.ThreeOfAKind()) {
            res.payout = symbols.get(r[0]-1).payout3;
        }
        if (res.TwoOfAKind()) {
            res.payout = symbols.get(r[0]-1).payout2;
        }
        if (res.OneOfAKind()) {
            res.payout = symbols.get(r[0]-1).payout1;
        }
        return res;
    }
}