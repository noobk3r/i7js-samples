
using iText.Kernel.Pdf;
using iText.Layout;
using iText.Layout.Element;
using System.IO;


namespace iText7SharpJumpstart
{
    public class C01E01_HelloWorld
    {

        public static void Main(string[] args)
        {
            //Define input & output paths
            string DESTDIR = "results/chapter01/";
            //Ensure the output directories exist
            Directory.CreateDirectory(DESTDIR);
            //Create the output path
            string DEST = DESTDIR + "HelloWorld.pdf";
            //Execute pdf creation
            new C01E01_HelloWorld().createPDF(DEST);
        }

        public void createPDF(string dest)
        {
            var writer = new PdfWriter(dest);
            var pdf = new PdfDocument(writer);
            var document = new Document(pdf);
            document.Add(new Paragraph("Hello World!"));
            document.Close();
        }
    }
}
