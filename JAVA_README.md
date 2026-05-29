# ☕ CodSoft Java Internship Projects

> **Developed by:** Abhishek Mishra
> Console-based Java projects built during CodSoft Java Programming Internship.

---

## 📁 Projects

### 🎮 Task 1 — Number Guessing Game
**File:** `NumberGame.java`

Ek fun number guessing game jisme player secret number guess karta hai with Hot/Cold hints, scoring system aur difficulty levels.

**Features:**
- 3 difficulty levels — Easy (1–50), Medium (1–100), Hard (1–500)
- Hot 🔥 / Cold ❄️ proximity hints
- Score system with difficulty multiplier (Hard = 3x score!)
- Multi-round support with final stats
- Rank system — Bronze → Silver → Gold → Diamond → Grandmaster
- First-guess bonus (+500 points)

**How to Run:**
```bash
javac NumberGame.java
java NumberGame
```

---

### 🏧 Task 2 — ATM Interface
**File:** `ATMInterface.java`

Console-based ATM simulator with PIN authentication aur basic banking operations.

**Features:**
- 4-digit PIN authentication
- Check balance
- Deposit money
- Withdraw money (insufficient balance check)
- Input validation with proper error messages

**Default Account:**
| Field | Value |
|---|---|
| Account Holder | Abhishek Mishra |
| Account Number | XXXX-XXXX-1234 |
| Initial Balance | ₹10,000.00 |
| PIN | `2007` |

**How to Run:**
```bash
javac ATMInterface.java
java ATMInterface
```

---

### 🎓 Task 5 — Student Management System
**File:** `StudentManagement.java`

Full-featured student management system with persistent data storage aur grade calculation.

**Features:**
- Add, view, update, remove students
- Auto-assigned roll numbers (starting 1001)
- Subject-wise marks entry
- Grade calculation (A+ to F) with GPA average
- Search by roll number or name (partial match)
- Class statistics — top student, pass rate, class average
- Persistent storage via Java Serialization (`students_data.dat`)

**Grade Scale:**
| Grade | Marks |
|---|---|
| A+ 🏆 | 90–100 |
| A ⭐ | 80–89 |
| B 👍 | 70–79 |
| C ✅ | 60–69 |
| D 📚 | 50–59 |
| E | 40–49 |
| F ❌ | Below 40 |

**How to Run:**
```bash
javac StudentManagement.java
java StudentManagement
```

> ⚠️ Student data `students_data.dat` file mein save hoti hai same directory mein.

---

## 🛠️ Requirements

- **Java:** JDK 8 or higher
- **IDE:** Any — IntelliJ IDEA, Eclipse, VS Code, ya simple terminal

---

## 🚀 Quick Start (Sabke liye ek saath)

```bash
# Compile all
javac NumberGame.java ATMInterface.java StudentManagement.java

# Run individually
java NumberGame
java ATMInterface
java StudentManagement
```

---

## 📌 Notes

- Saare projects pure console-based hain, koi external library nahi
- `StudentManagement` mein data automatically save hota hai exit pe
- ATM ka PIN hardcoded hai — `2007`

---

<div align="center">
  Made with ❤️ | CodSoft Java Internship | Abhishek Mishra
</div>
