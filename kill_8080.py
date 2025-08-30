import os
import platform
import subprocess

PORT = 8080

def find_pid(port):
    system = platform.system()

    if system == "Windows":
        # Windows 用 netstat 查 PID
        cmd = f'netstat -ano | findstr :{port}'
        output = subprocess.getoutput(cmd)
        for line in output.splitlines():
            parts = line.split()
            if len(parts) >= 5 and parts[1].endswith(f":{port}"):
                return parts[-1]
    else:
        # Linux/macOS 用 lsof 查 PID
        cmd = f'lsof -i :{port} -t'
        pid = subprocess.getoutput(cmd).strip()
        if pid.isdigit():
            return pid

    return None

def kill_pid(pid):
    system = platform.system()
    try:
        if system == "Windows":
            os.system(f"taskkill /PID {pid} /F")
        else:
            os.system(f"kill -9 {pid}")
        print(f"已終止 PID {pid} 的程序")
    except Exception as e:
        print(f"終止失敗: {e}")

def main():
    pid = find_pid(PORT)
    if pid:
        print(f"發現占用 {PORT} 埠的程序，PID: {pid}")
        kill_pid(pid)
    else:
        print(f"沒有程序占用 {PORT} 埠")

if __name__ == "__main__":
    main()
