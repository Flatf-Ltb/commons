package io.flatf.common.character;

/**
 * <pre>
 * 0    0x00  NUL  Null (空)
 * 1    0x01  SOH  Start of Heading (报头开始)
 * 2    0x02  STX  Start of Text (正文开始)
 * 3    0x03  ETX  End of Text (正文结束)
 * 4    0x04  EOT  End of Transmission (传输结束)
 * 5    0x05  ENQ  Enquiry (查询)
 * 6    0x06  ACK  Acknowledge (确认)
 * 21   0x15  NAK  Negative Acknowledge (否认)
 * 24   0x18  CAN  Cancel (取消)
 * 127  0x7F  DEL  Delete (删除)
 * </pre>
 *
 * @author yellow013
 */
public interface ASCII {

    /**
     * Null
     */
    char NUL = new String(new byte[]{0}).charAt(0);

    /**
     * Start of Heading
     */
    char SOH = new String(new byte[]{1}).charAt(0);

    /**
     * Start of Text
     */
    char STX = new String(new byte[]{2}).charAt(0);

    /**
     * End of Text
     */
    char ETX = new String(new byte[]{3}).charAt(0);

    /**
     * End of Transmission
     */
    char EOT = new String(new byte[]{4}).charAt(0);

    /**
     * Enquiry
     */
    char ENQ = new String(new byte[]{5}).charAt(0);

    /**
     * Acknowledgement
     */
    char ACK = new String(new byte[]{6}).charAt(0);

    /**
     * Negative Acknowledge
     */
    char NAK = new String(new byte[]{21}).charAt(0);

    /**
     * Cancel
     */
    char CAN = new String(new byte[]{24}).charAt(0);

    /**
     * Delete
     */
    char DEL = new String(new byte[]{127}).charAt(0);

}
