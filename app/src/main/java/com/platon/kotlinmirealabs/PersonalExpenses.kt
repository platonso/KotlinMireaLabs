package com.platon.kotlinmirealabs

import java.time.LocalDate

class Expense(val amount: Double, val category: String, val date: LocalDate) {
    fun displayExpense() = println("amount: $amount, category: $category, date: $date")
}

class ExpenseManager{
    private val expenses = mutableListOf<Expense>()

    fun addExpense(expense: Expense) {
        expenses.add(expense)
    }

    fun displayAllExpenses() {
        for (expense in expenses) {
            expense.displayExpense()
        }
    }

    fun calculateTotalByCategory() : Map<String, Double>{
        return expenses.groupBy { it.category}.mapValues { it.value.sumOf { it.amount } }
    }

    fun displayTotalByCategory(totalByCategory: Map<String, Double>) {
        for (category in totalByCategory.keys) {
            println("$category: ${totalByCategory[category]}")
        }
    }
}

fun main() {
    val expManager= ExpenseManager()
    expManager.addExpense(Expense(550.0, "food", LocalDate.of(2024, 9, 14)))
    expManager.addExpense(Expense(840.0, "transport", LocalDate.of(2024, 9, 14)))
    expManager.addExpense(Expense(399.0, "food", LocalDate.of(2024, 9, 14)))
    expManager.addExpense(Expense(450.0, "food", LocalDate.of(2024, 9, 14)))
    expManager.addExpense(Expense(400.0, "sport", LocalDate.of(2024, 9, 14)))
    expManager.addExpense(Expense(625.0, "transport", LocalDate.of(2024, 9, 14)))

    println("\nВсе расходы:")
    expManager.displayAllExpenses()

    println("\nСумма всех расходов по каждой категории:")
    val totalByCategory = expManager.calculateTotalByCategory()
    expManager.displayTotalByCategory(totalByCategory)
}

