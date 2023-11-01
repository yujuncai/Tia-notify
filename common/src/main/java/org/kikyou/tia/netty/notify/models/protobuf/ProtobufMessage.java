package org.kikyou.tia.netty.notify.models.protobuf;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Data;

@Data
@ProtobufClass
public class ProtobufMessage {
    @Protobuf(fieldType= FieldType.UINT64, order=1)
    private  Long id;
    @Protobuf(fieldType= FieldType.STRING, order=2)
    private  String name;
}
