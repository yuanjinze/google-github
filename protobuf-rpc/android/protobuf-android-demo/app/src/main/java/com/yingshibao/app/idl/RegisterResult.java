// Generated by the protocol buffer compiler.  DO NOT EDIT!

package com.yingshibao.app.idl;

@SuppressWarnings("hiding")
public final class RegisterResult extends
    com.google.protobuf.nano.MessageNano {

  private static volatile RegisterResult[] _emptyArray;
  public static RegisterResult[] emptyArray() {
    // Lazily initializes the empty array
    if (_emptyArray == null) {
      synchronized (
          com.google.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
        if (_emptyArray == null) {
          _emptyArray = new RegisterResult[0];
        }
      }
    }
    return _emptyArray;
  }

  // required string errorMessage = 1;
  public java.lang.String errorMessage;

  // optional int32 userId = 2;
  public int userId;

  // optional string sessionId = 3;
  public java.lang.String sessionId;

  public RegisterResult() {
    clear();
  }

  public RegisterResult clear() {
    errorMessage = "";
    userId = 0;
    sessionId = "";
    cachedSize = -1;
    return this;
  }

  @Override
  public void writeTo(com.google.protobuf.nano.CodedOutputByteBufferNano output)
      throws java.io.IOException {
    output.writeString(1, this.errorMessage);
    if (this.userId != 0) {
      output.writeInt32(2, this.userId);
    }
    if (!this.sessionId.equals("")) {
      output.writeString(3, this.sessionId);
    }
    super.writeTo(output);
  }

  @Override
  protected int computeSerializedSize() {
    int size = super.computeSerializedSize();
    size += com.google.protobuf.nano.CodedOutputByteBufferNano
        .computeStringSize(1, this.errorMessage);
    if (this.userId != 0) {
      size += com.google.protobuf.nano.CodedOutputByteBufferNano
          .computeInt32Size(2, this.userId);
    }
    if (!this.sessionId.equals("")) {
      size += com.google.protobuf.nano.CodedOutputByteBufferNano
          .computeStringSize(3, this.sessionId);
    }
    return size;
  }

  @Override
  public RegisterResult mergeFrom(
          com.google.protobuf.nano.CodedInputByteBufferNano input)
      throws java.io.IOException {
    while (true) {
      int tag = input.readTag();
      switch (tag) {
        case 0:
          return this;
        default: {
          if (!com.google.protobuf.nano.WireFormatNano.parseUnknownField(input, tag)) {
            return this;
          }
          break;
        }
        case 10: {
          this.errorMessage = input.readString();
          break;
        }
        case 16: {
          this.userId = input.readInt32();
          break;
        }
        case 26: {
          this.sessionId = input.readString();
          break;
        }
      }
    }
  }

  public static RegisterResult parseFrom(byte[] data)
      throws com.google.protobuf.nano.InvalidProtocolBufferNanoException {
    return com.google.protobuf.nano.MessageNano.mergeFrom(new RegisterResult(), data);
  }

  public static RegisterResult parseFrom(
          com.google.protobuf.nano.CodedInputByteBufferNano input)
      throws java.io.IOException {
    return new RegisterResult().mergeFrom(input);
  }
}
