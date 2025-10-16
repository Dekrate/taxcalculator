

# Detailed Justification for the Refactoring of the Tax Calculator Application

## 1. Introduction

This document provides a comprehensive rationale for the structural and logical modifications applied to the Tax Calculator application. The original implementation, while functional for its immediate purpose, was characteristic of a procedural script confined within a `main` method. The refactoring effort was undertaken to transition the codebase into a more robust, maintainable, and scalable object-oriented (OO) design.

The primary goals of this refactoring were:

* **Improve Maintainability:** Make the code easier to modify and extend with new features (e.g., new contract types, changing tax laws).
* **Enhance Readability:** Structure the code so that its intent is clear, reducing the cognitive load for future developers.
* **Increase Robustness:** Minimize the potential for bugs by enforcing data integrity and logical consistency.
* **Adhere to Professional Standards:** Align the code with established Java best practices and conventions, as identified by static analysis tools like SonarLint and Checkstyle.

---

## 2. From a Procedural Script to an Object-Oriented Model

### 2.1. Analysis of the Original Structure

The initial code was a single, monolithic block within the `public static void main` method.

* **Data and Logic were Tightly Coupled:**
  All variables (income, contributions, taxes) were declared as either local variables or static fields, accessible and modifiable from anywhere within the `main` method's scope. There was no clear ownership of data.

* **High Code Duplication:**
  The calculation logic for “Employment” (`E`) and “Civil” (`C`) contracts was largely copy-pasted, with minor variations.
  This violates the *Don’t Repeat Yourself (DRY)* principle. A change in a common calculation (e.g., the health contribution formula) would require identical edits in two separate places, doubling the risk of error.

* **Poor Scalability:**
  To add a third contract type, a developer would need to add another large `else if` block, duplicating even more code and making the `main` method increasingly complex and unmanageable.

### 2.2. The Rationale for an Object-Oriented Approach

Object-Oriented Programming provides mechanisms to solve these problems by modeling the application around objects that group data (state) and the operations on that data (behavior).

* **Encapsulation:**
  By creating classes, we can bundle related data and methods.
  The refactored design introduces a central class (e.g., `TaxCalculator` or `SocialContributions`) that owns the financial data (income, contributions, etc.).
  Its internal state is hidden from the outside world by declaring fields as `private`. This prevents other parts of the program from putting the object into an inconsistent state.
  This directly addresses **SonarLint: S1170 (Fields should be private)**.

* **Abstraction and Separation of Concerns:**
  The monolithic logic was decomposed into distinct, abstract responsibilities:

    1. **Data Input:** Logic for reading from the console.
    2. **Business Logic:** The core calculations for taxes and contributions.
    3. **Data Output:** Logic for displaying the results.

  This separation is fundamental to building maintainable software.
  For example, with this new design, we could replace the console input with a graphical user interface (GUI) by only changing the data input component, without touching the core calculation engine.

---

## 3. Detailed Design and Structural Changes

### 3.1. Class and Abstraction Design

* **`TaxCalculator` / `SocialContributions` Class:**
  A new class was introduced to serve as the primary engine for calculations.
  It is no longer a collection of static methods but a true object that can be instantiated.
  It holds the state of a single calculation (the income, the contract type, and all resulting values) as private instance fields.

* **`ContractType` Enum:**
  The use of char literals (`'E'`, `'C'`) is a *code smell*.
  It is not type-safe (any character can be used) and lacks semantic meaning.
  An enum was introduced to model the concept of a contract type.

    * **Type Safety:**
      The compiler guarantees that a variable of type `ContractType` can only hold predefined values (`EMPLOYMENT`, `CIVIL`).
      This eliminates an entire class of potential bugs from invalid inputs.

    * **Readability and Intent:**
      The code `if (contractType == ContractType.EMPLOYMENT)` is self-documenting and far clearer than `if (contractType == 'E')`.

    * **Scalability:**
      Adding a new contract type becomes a matter of adding a new value to the enum.
      The compiler can then help identify all the `switch` or `if` statements that need to be updated to handle the new case.

---

### 3.2. Method Decomposition and the Single Responsibility Principle (SRP)

The large `main` method was broken down into smaller methods, each with a single, well-defined responsibility.

* **`collectData()`:**
  Responsible solely for interacting with the user and gathering input.
  It isolates the `System.in` and `BufferedReader` logic.

* **`calculate()`:**
  This is the core business logic method. Its only job is to execute the sequence of calculations based on the object’s state (income and contract type).
  It does not perform I/O, making the core logic pure and easily testable.

* **`printResults()`:**
  This method’s only responsibility is to format and display the calculated results to the console.
  It is decoupled from the calculation itself.

* **Private Helper Methods:**
  The `calculate()` method itself can be further decomposed into smaller, private methods like `calculateSocialContributions()`, `calculateHealthContribution()`, and `calculateFinalTax()`.
  This makes the high-level `calculate()` method read like a summary of the business process, improving clarity.

---

### 3.3. Variable and Data Management

* **Elimination of “Magic Numbers”:**
  The original code was littered with numeric literals like `9.76`, `1.5`, `0.18`.
  These “magic numbers” have no context.

    * **Justification:**
      They were replaced with `private static final` constants with descriptive names (e.g., `PENSION_CONTRIBUTION_RATE`, `TAX_RATE`).
      This was driven by **Checkstyle: MagicNumber** and **SonarLint: S109**.

    * **Benefit:**
      This dramatically improves readability.
      More importantly, it enhances maintainability.
      If a tax rate changes, the developer only needs to update a single constant at the top of the file, and the change is safely propagated throughout the code.

* **Improved Naming Conventions:**
  Vague, abbreviated, or non-English names (`s_emerytalna`, `KUP`, `zaliczkaNaPod`) were replaced with descriptive, English names following the Java camelCase convention (`pensionContribution`, `taxDeductibleExpenses`, `taxAdvance`).

    * **Justification:**
      Code is read far more often than it is written.
      Clear names reduce the time it takes for another developer to understand the code’s purpose.
      This aligns with **Checkstyle: MemberName** and **SonarLint: S116**.

---

### 3.4. Logic and Control Flow Simplification

* **Consolidation of Duplicated Logic:**
  The primary achievement of the refactoring was the elimination of the duplicated calculation blocks.
  The new `calculate()` method contains a single, unified path for all calculations.

* **Handling Variations:**
  Differences between contract types (such as the formula for `taxDeductibleExpenses`) are handled inside small, specific `if` or `switch` statements based on the `ContractType` enum.
  The common logic is written only once.
  This directly follows the DRY principle and addresses **SonarLint: S1126 (Code duplication)**.
  The resulting control flow is simpler, less nested, and easier to follow.

---

## 4. Conclusion

The transformation of the Tax Calculator was not a cosmetic exercise.
It was a fundamental re-architecting from a fragile, procedural script into a well-structured, object-oriented program.

By applying core principles like encapsulation, the *Single Responsibility Principle*, and *DRY*, and by leveraging language features like enums, the new codebase is now:

* **Safer:** Type safety and private fields prevent common errors.
* **More Readable:** Self-documenting names and clear separation of concerns make the code’s intent obvious.
* **Easier to Maintain and Extend:** Changes to tax laws or the addition of new contract types can now be implemented with minimal, localized effort.
* **Testable:** The decoupled business logic in the `calculate()` method can be unit-tested in isolation, a task that was nearly impossible with the original monolithic `main` method.

This refactoring process has elevated the code to a professional standard, creating a solid foundation for its future evolution.
