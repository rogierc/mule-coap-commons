package nl.teslanet.mule.connectors.coap.options;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.coap.CoAP;

public class ETag implements Cloneable
{
    private byte[] etag= null;
    
    public ETag( byte[] etag )
    {
        this.etag= etag.clone();
    }
    
    public ETag( String hexString )
    {
        etag = new byte[hexString.length() / 2];
        for (int i = 0; i < etag.length; i++) {
          int index = i * 2;
          int v = Integer.parseInt(hexString.substring(index, index + 2), 16);
          etag[i] = (byte) v;
        }
    }
    
    public byte[] asBytes( )
    {
        return etag.clone();
    }
    
    public String toString()
    {
        return Utils.toHexString( etag );
    }
    
    public String asUTF8()
    {
        return new String( etag, CoAP.UTF8_CHARSET );
    }
    
    static public ETag create( byte[] etag )
    {
        return new ETag( etag );
    }

    
    static public List<ETag> getList( List<byte[]> etags )
    {
        LinkedList<ETag> result= new LinkedList<ETag>();
        for ( byte[] etag : etags )
        {
            result.add( new ETag( etag ));
        }
        return result;
    }

    public boolean equals( ETag other )
    {
        if ( this == other ) return true;
        if ( this.etag.length != other.etag.length ) return false;
        for ( int i= 0 ; i < this.etag.length && i < other.etag.length ; i++ )
        {
            if ( this.etag[i] != other.etag[i] ) return false;
        }
        return true;
    }


    public boolean isIn( Collection<ETag> etags )
    {
        for ( ETag e : etags )
        {
            if ( this.equals( e )) return true;
        }
        return false;
    }
}
