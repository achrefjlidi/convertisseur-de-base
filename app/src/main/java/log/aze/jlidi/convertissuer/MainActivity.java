package log.aze.jlidi.convertissuer;

        import android.content.DialogInterface;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.LinearLayout;
        import android.widget.RelativeLayout;
        import android.widget.Spinner;
        import android.widget.TextView;

        import java.math.BigDecimal;

        import static java.lang.Math.pow;

public class MainActivity extends AppCompatActivity {
    Button btnArray[];
    TextView textViewArray[];
    EditText editTextNumber;
    Spinner mySpinner;
    int spinnerSelected = 0,max=8;
    String j,dec,bin,oct,hex;;
    String str ="";
    Boolean ifDotAdded=Boolean.FALSE,keyBoardOn=Boolean.FALSE;
    LinearLayout linLayout[];
    RelativeLayout keyboardlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Base Converter");




        //Variable Initialisation starts
        btnArray = new Button[20];
        textViewArray=new TextView[8];
        linLayout=new LinearLayout[4];
        final int[] idArray = {R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9, R.id.buttonA, R.id.buttonB, R.id.buttonC, R.id.buttonD, R.id.buttonE, R.id.buttonF, R.id.buttonDot, R.id.buttonBackSpace, R.id.buttonClear};
        final int[] idTextViewAray = {R.id.textView2,R.id.textViewDecimal,R.id.textView3,R.id.textViewBinary,R.id.textView4,R.id.textViewOctal,R.id.textView5,R.id.textViewHexaDecimal};
        final int[] idlinLayout={R.id.c1,R.id.c2,R.id.c3,R.id.c4};
        mySpinner = (Spinner) findViewById(R.id.spinner1);
        mySpinner.setSelection(0);
        editTextNumber = (EditText) findViewById(R.id.editTextnumber);
        keyboardlayout=(RelativeLayout)findViewById(R.id.keyboardLayout);
        keyboardlayout.setVisibility(View.INVISIBLE);



        //input listener section
        editTextNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(keyBoardOn==Boolean.FALSE)
                {
                    keyboardlayout.setVisibility(View.VISIBLE);
                    keyBoardOn=Boolean.TRUE;
                }
            }
        });

        for (int i = 0; i < idArray.length; i++) {
            btnArray[i] = (Button) findViewById(idArray[i]);
        }
        for (int i = 0; i < idTextViewAray.length; i++) {
            textViewArray[i] = (TextView) findViewById(idTextViewAray[i]);
        }
        for (int i = 0; i < idlinLayout.length; i++) {
            linLayout[i] = (LinearLayout) findViewById(idlinLayout[i]);
        }


        //Spinner selection
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < idArray.length; i++) {
                    btnArray[i].setClickable(true);
                    btnArray[i].setVisibility(View.VISIBLE);
                }
                for (int i = 0; i < idlinLayout.length; i++) {
                    linLayout[i].setVisibility(View.VISIBLE);
                }
                resetAll();

                switch (position) {
                    case 0:
                        for (int i = 10; i <= 15; i++) {
                            // btnArray [i].setClickable(false);
                            btnArray[i].setVisibility(View.GONE);
                            setVisibilityTextView(0);
                        }
                        dec=editTextNumber.getText().toString();
                        bin=textViewArray[3].getText().toString();
                        oct=textViewArray[5].getText().toString();
                        hex=textViewArray[7].getText().toString();
                        spinnerSelected = 0;
                        linLayout[0].setVisibility(View.GONE);
                        break;
                    case 1:
                        spinnerSelected = 1;
                        dec=textViewArray[1].getText().toString();
                        bin=editTextNumber.getText().toString();
                        oct=textViewArray[5].getText().toString();
                        hex=textViewArray[7].getText().toString();
                        linLayout[1].setVisibility(View.GONE);
                        for (int i = 2; i <= 15; i++) {
                            // btnArray [i].setClickable(false);
                            btnArray[i].setVisibility(View.GONE);
                            setVisibilityTextView(2);
                        }
                        break;
                    case 2:
                        linLayout[2].setVisibility(View.GONE);
                        spinnerSelected = 2;
                        dec=textViewArray[1].getText().toString();
                        bin=textViewArray[3].getText().toString();
                        oct=editTextNumber.getText().toString();
                        hex=textViewArray[7].getText().toString();
                        for (int i = 8; i <= 15; i++) {
                            //  btnArray [i].setClickable(false);
                            btnArray[i].setVisibility(View.GONE);
                            setVisibilityTextView(4);
                        }
                        break;
                    case 3:
                        dec=textViewArray[1].getText().toString();
                        bin=textViewArray[3].getText().toString();
                        oct=textViewArray[5].getText().toString();
                        hex=editTextNumber.getText().toString();
                        linLayout[3].setVisibility(View.GONE);
                        spinnerSelected = 3;
                        setVisibilityTextView(6);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Button Part Start




        for (int i=0; i<idArray.length; i++) {
            final int k=i;
            btnArray [i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    char x=' ';
                    j=btnArray[k].getTag().toString();
                    if (j.equalsIgnoreCase("del"))
                    {
                        if (str != null && str.length() > 0) {
                            x=str.charAt(str.length()-1);
                            str = str.substring(0, str.length()-1);
                            if(str.length()>0  && str.charAt(str.length()-1)!='.')
                                convertAll();
                            if(str.length()==0)
                                resetAll();
                        }
                        if(x=='.')
                        {
                            ifDotAdded=Boolean.FALSE;
                        }

                    }

                    if (j.equalsIgnoreCase("0")|j.equalsIgnoreCase("1")|j.equalsIgnoreCase("2")|j.equalsIgnoreCase("3")|j.equalsIgnoreCase("4")|j.equalsIgnoreCase("5")|j.equalsIgnoreCase("6")|j.equalsIgnoreCase("7")|j.equalsIgnoreCase("8")|j.equalsIgnoreCase("9")|j.equalsIgnoreCase("A")|j.equalsIgnoreCase("B")|j.equalsIgnoreCase("C")|j.equalsIgnoreCase("D")|j.equalsIgnoreCase("E")|j.equalsIgnoreCase("F"))
                    {
                        if ((isStrFixedAddable(str) && !ifDotAdded) || (isStrPointAddable(str) && ifDotAdded)) {
                            str = str + j;
                            convertAll();
                        }
                    }
                    if(j.equalsIgnoreCase("."))
                    {
                        if(ifDotAdded==Boolean.FALSE && isStrPointAddable(str))
                        {
                            if(str.equalsIgnoreCase(""))
                                str=str+"0";
                            str=str+j;
                            ifDotAdded=Boolean.TRUE;
                        }

                    }
                    if(j.equalsIgnoreCase("reset"))
                    {
                        resetAll();
                    }

                    editTextNumber.setText(str, TextView.BufferType.SPANNABLE);

                }
            });

        }
        //Button Part End
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share_result:

                switch (spinnerSelected) {
                    case 0:
                        dec=editTextNumber.getText().toString();
                        bin=textViewArray[3].getText().toString();
                        oct=textViewArray[5].getText().toString();
                        hex=textViewArray[7].getText().toString();
                        break;
                    case 1:
                        dec=textViewArray[1].getText().toString();
                        bin=editTextNumber.getText().toString();
                        oct=textViewArray[5].getText().toString();
                        hex=textViewArray[7].getText().toString();
                        break;
                    case 2:
                        dec=textViewArray[1].getText().toString();
                        bin=textViewArray[3].getText().toString();
                        oct=editTextNumber.getText().toString();
                        hex=textViewArray[7].getText().toString();
                        break;
                    case 3:
                        dec=textViewArray[1].getText().toString();
                        bin=textViewArray[3].getText().toString();
                        oct=textViewArray[5].getText().toString();
                        hex=editTextNumber.getText().toString();
                }



                Intent sharingIntent1 = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent1.setType("text/plain");
                String shareBody1 = "Decimal: "+dec+"\n"+"Binary: "+bin+"\n"+"Octal: "+oct+"\n"+"Hexa Decimal: "+hex+"\n"+"\n"+"Results from Base Converter."+"\n"+"Get the APP from:\n"+"https://play.google.com/store/apps/details?id=com.etechlearnings.numbersystembaseconverter";
                sharingIntent1.putExtra(android.content.Intent.EXTRA_SUBJECT, "Base Converter");
                sharingIntent1.putExtra(android.content.Intent.EXTRA_TEXT, shareBody1);
                startActivity(Intent.createChooser(sharingIntent1, "Share via"));
                return true;

            case R.id.action_share_app:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "https://play.google.com/store/apps/details?id=com.etechlearnings.numbersystembaseconverter";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Base Converter");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;
            case R.id.action_rate_app:
                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.etechlearnings.numbersystembaseconverter");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                return true;

            case R.id.action_know_rules:
                Uri uri1 = Uri.parse("http://www.etechlearnings.com/fundamental/conversion.html");
                Intent intent1 = new Intent(Intent.ACTION_VIEW, uri1);
                startActivity(intent1);
                return true;
            case R.id.action_exit:
                System.exit(0);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
    @Override
    public void onBackPressed() {
        if(keyBoardOn==Boolean.TRUE)
        {
            keyboardlayout.setVisibility(View.INVISIBLE);
            keyBoardOn=Boolean.FALSE;
        }
        else {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Exit Application")
                    .setMessage("Want to Exit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }
    //Custom Functions
    void resetAll()//reset funtion
    {
        str="";
        editTextNumber.setText("", TextView.BufferType.SPANNABLE);
        ifDotAdded=Boolean.FALSE;
        for (int i = 1; i < 8; i=i+2) {

            textViewArray[i].setText("", TextView.BufferType.SPANNABLE);
        }
    }

    void setVisibilityTextView(int p)//textview visibility control function
    {
        textViewArray[p].setVisibility(View.GONE);
        textViewArray[p+1].setVisibility(View.GONE);
        for(int k=0;k<8;k++)
        {
            if(k!=p && k!=p+1)
                textViewArray[k].setVisibility(View.VISIBLE);
        }
    }

    String convertD2O(int d, String str1)//decimal to other
    {
        String fixed="",decpoint="0",convertedFixed="",convertedDecpoint="";
        int pointPos=-1;
        long num;
        double numD;
        pointPos=str1.indexOf(".");
        if(pointPos>=0)
        {
            fixed=str1.substring(0,pointPos);
            decpoint=str1.substring(pointPos,str1.length());
            numD= Double.parseDouble(decpoint);
            int m = 0;
            while ((numD - (int) numD) != 0) {
                numD = numD * d;
                convertedDecpoint = convertedDecpoint + Integer.toHexString((int) numD).toUpperCase();
                numD = numD - (int) numD;
                m++;
            }
        }
        else{
            fixed=str1;
        }
        num=Long.parseLong(fixed);
        while(num>0)
        {
            convertedFixed=convertedFixed+Integer.toHexString((int)(num%d)).toUpperCase();
            num=num/d;
        }
        convertedFixed=new StringBuilder(convertedFixed).reverse().toString();
        if(pointPos==-1)
            return (convertedFixed);
        else {
            if (convertedFixed.equalsIgnoreCase(""))
                convertedFixed="0";
            return (convertedFixed + "." + convertedDecpoint);
        }
    }




    String convertO2D(int d, String str1)//other to decimal
    {
        String fixed="",decpoint="",convertedFixed="",convertedDecpoint="",test="";
        int pointPos=-1;

        BigDecimal numD= BigDecimal.valueOf(0);
        long num= 0;
        pointPos=str1.indexOf(".");
        if(pointPos>=0)
        {
            int m=1;
            for(int i=pointPos+1;i<str1.length();i++,m++)
            {

                numD=numD.add(BigDecimal.valueOf((1.0/pow(d,m))*getValue(str1.charAt(i))));
            }

            m=0;
            int k=0;
            for(int i=pointPos-1;i>=0;i--,m++)
            {
                num=num+(long) (pow(d,m)*getValue(str1.charAt(i)));
            }
        }

        else{
            int m=0;
            int k=0;
            for(int i=str1.length()-1;i>=0;i--,m++)
            {
                num=num+ (long) (pow(d,k))*getValue(str1.charAt(i));
                k++;
            }
        }

        if(pointPos==-1) {
            return (Long.toString(num) );
        }
        else {
            String t=numD.toString();
            return (Long.toString(num) + t.substring(1,t.length()));
        }
    }




    String convert(int s,int d,String str1)//other to other
    {
        return (convertD2O(d,convertO2D(s,str1)));
    }

    int getValue(char c)
    {
        if(c=='A' || c=='a')
            return 10;
        if(c=='B' || c=='b')
            return 11;
        if(c=='C' || c=='c')
            return 12;
        if(c=='D' || c=='d')
            return 13;
        if(c=='E' || c=='e')
            return 14;
        if(c=='F' || c=='f')
            return 15;
        else
            return (Character.getNumericValue(c));
    }

    Boolean isStrFixedAddable(String str1){
        String fixed="";
        int pointPos=-1;
        pointPos=str1.indexOf(".");
        if(pointPos>=0)
        {
            fixed=str1.substring(0,pointPos);
        }
        else
            fixed=str1;

        if ((spinnerSelected!=1 && fixed.length()==max) ||(spinnerSelected==1 && fixed.length()==2*max))
        {
            return Boolean.FALSE;
        }
        else
            return Boolean.TRUE;

    }
    Boolean isStrPointAddable(String str1){
        String decpoint="";
        int pointPos=-1;
        pointPos=str1.indexOf(".");
        if(pointPos>=0)
        {
            decpoint=str1.substring(pointPos,str1.length());
        }

        if (decpoint.length()==max )
        {
            return Boolean.FALSE;
        }
        else
            return Boolean.TRUE;

    }


    void convertAll() {
        if(spinnerSelected==0)
        {
            textViewArray[3].setText(convertD2O(2,str));
            textViewArray[5].setText(convertD2O(8,str));
            textViewArray[7].setText(convertD2O(16,str));
        }
        if(spinnerSelected==1)
        {
            textViewArray[1].setText(convertO2D(2,str));
            textViewArray[5].setText(convert(2,8,str));
            textViewArray[7].setText(convert(2,16,str));
        }
        if(spinnerSelected==2) {
            textViewArray[1].setText(convertO2D(8,str));
            textViewArray[3].setText(convert(8,2,str));
            textViewArray[7].setText(convert(8,16,str));
        }
        if(spinnerSelected==3)
        {
            textViewArray[1].setText(convertO2D(16,str));
            textViewArray[3].setText(convert(16,2,str));
            textViewArray[5].setText(convert(16,8,str));
        }
    }


}