import socket
import time
from config import IP, PORT


def main():
    with socket.socket() as s:
        s.bind((IP, PORT))
        s.listen(1)
        while True:
            client, _ = s.accept()
            with client:
                client.sendall(b"Hello, socket.\n")
                time.sleep(3)
                client.sendall(b"Hello, socket!!!!!!\n")


if __name__ == "__main__":
    main()
